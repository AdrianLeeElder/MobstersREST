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
        currentAction.setRunning(true);
        currentAction.run();
        for (int failureCount = 0; failureCount < MAX_ATTEMPTS; failureCount++) {
            try {
                humanBotService.randomSleep(1000, 2000);

                if (currentAction.isFinished()) {
                    currentAction.response();

                    log.trace("Action: {} finished.", currentAction.getName());
                    return;
                }

            } catch (Exception e) {
                log.error("Action failed: {}. Attempt number: {}, Message: {}",
                        currentAction.getName(),
                        failureCount,
                        e.getMessage());

                humanBotService.randomSleep(5000, 7000);
            }
        }

        throw new ActionFailedException(currentAction);
    }
}
