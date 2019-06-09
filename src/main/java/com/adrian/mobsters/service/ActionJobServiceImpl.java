package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.config.ActionExecutorProperties;
import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ActionFailedException;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.ProxyRepository;
import com.adrian.mobsters.service.proxy.ProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Execute the actions stored in an ActionJob.
 * <p>
 * TODO: figure out what I want to do with old ActionJobs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ActionJobServiceImpl implements ActionJobService {
    private final ActionExecutor actionExecutor;
    private final ActionService actionService;
    private final ActionJobRepository actionJobRepository;
    private final ActionExecutorProperties actionExecutorProperties;
    private final ApplicationContext applicationContext;
    private final ProxyService proxyService;
    private final ProxyRepository proxyRepository;

    @Override
    @Async
    public void run(ActionJob actionJob) {
        try {
            runActionJob(actionJob);
            actionJob.setComplete();
            actionJobRepository.save(actionJob);
        } catch (ActionFailedException e) {
            handleActionJobFailure(e, actionJob);
        }
    }

    private void runActionJob(ActionJob actionJob) throws ActionFailedException {
        log.debug("Executing action job {}", actionJob);
        executeActions(actionJob);
    }

    private void handleActionJobFailure(Throwable e, ActionJob actionJob) {
        log.error("Unable to execute action.", e);
        if (actionJobHasMaximumFailures(actionJob)) {
            actionJob.setFrozen();
            log.debug("Deleting action job: {}", actionJob);
        } else {
            actionJob.setFailureCount(actionJob.getFailureCount() + 1);
            log.debug("Incrementing failure count for action job: {}", actionJob);
        }

        actionJobRepository.save(actionJob);

        requeueNonFrozenActionJob(actionJob);
    }

    private void requeueNonFrozenActionJob(ActionJob actionJob) {
        if (!actionJob.isFrozen()) {
            log.debug("ActionJob failed and is not frozen, queueing it back up: {}", actionJob);
            run(actionJob);
        }
    }

    private boolean actionJobHasMaximumFailures(ActionJob actionJob) {
        return actionJob.getFailureCount() > actionExecutorProperties.getMaxFailures();
    }

    private void executeActions(ActionJob actionJob) throws ActionFailedException {
        Proxy proxy = proxyService.getAvailableProxy();
        proxy.setAttempts(proxy.getAttempts() + 1);
        proxyRepository.save(proxy);
        ChromeDriver chromeDriver = getWebClient();

        try {
            proxy.setInUse(true);
            proxyRepository.save(proxy);
            actionJob.setRunning();
            actionJobRepository.save(actionJob);
            processActionJobList(actionJob, chromeDriver);

            proxy.setSuccesses(proxy.getSuccesses() + 1);
            proxyRepository.save(proxy);

            actionJob.setComplete();
            actionJobRepository.save(actionJob);
        } catch (Exception ex) {
            log.error("Uncaught exception: ", ex);
        } finally {
            proxy.setInUse(false);
            proxyRepository.save(proxy);

            log.info("Proxy was set to in use false.");
            chromeDriver.close();
        }
    }

    private void processActionJobList(ActionJob actionJob, ChromeDriver chromeDriver) throws ActionFailedException {
        for (Action action : actionJob.getActionList()) {
            runAction(actionJob, action);
            actionExecutor.executeAction(chromeDriver, getAbstractAction(actionJob, action));
            action.setComplete();
            actionJobRepository.save(actionJob);
        }
    }

    private AbstractAction getAbstractAction(ActionJob actionJob, Action action) {
        AbstractAction abstractAction = actionService.getAction(action.getName());
        abstractAction.setMobsterUsername(actionJob.getMobster().getUsername());

        return abstractAction;
    }

    protected ChromeDriver getWebClient() {
        return new ChromeDriver(
                (ChromeOptions) applicationContext.getBean("chromeOptions")
        );
    }

    private void runAction(ActionJob actionJob, Action action) {
        action.setRunning();
        actionJobRepository.save(actionJob);
    }
}
