package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.actions.Refresh;
import com.adrian.mobsters.exception.ActionFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

/**
 * @author Adrian
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActionExecutorImpl implements ActionExecutor {

    private static final int MAX_ATTEMPTS = 12;

    private final HumanBotService humanBotService;
    private final ActionService actionService;

    @Override
    public void executeAction(ChromeDriver chromeDriver, AbstractAction currentAction) throws ActionFailedException {
        currentAction.setChromeDriver(chromeDriver);
        setAndExecuteCurrentAction(currentAction);

        for (int failureCount = 0; failureCount < MAX_ATTEMPTS; failureCount++) {
            if (failureCount > 0) {
                humanBotService.randomSleep(1500 * (failureCount), 2300 * (failureCount));
            }

            if (currentAction.isFinished()) {
                finishAction(currentAction);
                return;
            }

            log.debug("Action not successfully. Attempt number: {}", failureCount);
            switch (failureCount) {
                case 3:
//                    refresh(chromeDriver);
                case 7:
                    log.debug("Attempting to execute action: {} again.", currentAction.getName());
                    currentAction.run();
                    break;
            }
        }

        throw new ActionFailedException(currentAction);
    }

    private void refresh(ChromeDriver chromeDriver) throws ActionFailedException {
        Refresh refresh = (Refresh) actionService.getAction("Refresh");
        refresh.setChromeDriver(chromeDriver);
        refresh.run();
        log.info("Attempting a page refresh.");
    }

    private void finishAction(AbstractAction currentAction) throws ActionFailedException {
        currentAction.response();

        log.debug("Action: {} finished.", currentAction.getName());
    }

    private void setAndExecuteCurrentAction(AbstractAction currentAction) throws ActionFailedException {
        //currentAction = mobster.getActions().peek();
        currentAction.setRunning(true);

        log.debug("Executing action: {}", currentAction.getName());

        currentAction.run();
    }
}
