package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.config.ActionExecutorProperties;
import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ActionFailedException;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import com.adrian.mobsters.service.proxy.ProxyService;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ActionJobReactiveRepository actionJobReactiveRepository;
    private final ActionExecutorProperties actionExecutorProperties;
    private final ApplicationContext applicationContext;
    private final ProxyService proxyService;
    private final ProxyReactiveRepository proxyReactiveRepository;

    @Override
    @Async
    public void run(ActionJob actionJob) {
        try {
            runActionJob(actionJob);
        } catch (ActionFailedException e) {
            handleActionJobFailure(e, actionJob);
        }
    }

    private void runActionJob(ActionJob actionJob) throws ActionFailedException {
        log.debug("Executing action job {}", actionJob);
        executeActions(actionJob);
        actionJob.setComplete(true);
        actionJobReactiveRepository.save(actionJob).subscribe();
    }

    private void handleActionJobFailure(Throwable e, ActionJob actionJob) {
        log.error("Unable to execute action.", e);
        if (actionJobHasMaximumFailures(actionJob)) {
            actionJob.setFrozen(true);
            log.debug("Deleting action job: {}", actionJob);
        } else {
            actionJob.setFailureCount(actionJob.getFailureCount() + 1);
            log.debug("Incrementing failure count for action job: {}", actionJob);
        }

        actionJob.setQueued(false);
        actionJobReactiveRepository.save(actionJob).subscribe();

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
        proxyReactiveRepository.save(proxy).subscribe();
        WebClient webClient = getWebClient();
        setWebClientProxy(webClient, proxy);

        try {
            proxy.setInUse(true);
            proxyReactiveRepository.save(proxy).subscribe();

            processActionJobList(actionJob, webClient);

            proxy.setSuccesses(proxy.getSuccesses() + 1);
            proxyReactiveRepository.save(proxy).subscribe();
        } catch (Exception ex) {
            log.error("Uncaught exception {}", ex);
        } finally {
            proxy.setInUse(false);
            proxyReactiveRepository.save(proxy).subscribe();

            log.info("Proxy was set to in use false.");
        }
    }

    private void processActionJobList(ActionJob actionJob, WebClient webClient) throws ActionFailedException {
        for (Action action : actionJob.getActionList()) {
            runAction(actionJob, action);
            actionExecutor.executeAction(webClient, getAbstractAction(actionJob, action));
            action.setRunning(false);
            action.setComplete(true);
            actionJobReactiveRepository.save(actionJob).block();
        }
    }

    private void setWebClientProxy(WebClient webClient, Proxy proxy) {
        webClient.getOptions().setProxyConfig(new ProxyConfig(proxy.getHost(), proxy.getPort()));

        log.debug("Creating webclient with proxy: {}", proxy);
    }

    private AbstractAction getAbstractAction(ActionJob actionJob, Action action) {
        AbstractAction abstractAction = actionService.getAction(action.getName());
        abstractAction.setMobsterUsername(actionJob.getMobster().getUsername());

        return abstractAction;
    }

    protected WebClient getWebClient() {
        return (WebClient) applicationContext.getBean("webClient");
    }

    private void runAction(ActionJob actionJob, Action action) {
        action.setRunning(true);
        actionJobReactiveRepository.save(actionJob).block();
    }
}
