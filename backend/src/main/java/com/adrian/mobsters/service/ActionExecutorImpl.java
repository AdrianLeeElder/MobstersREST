package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.actions.Refresh;
import com.adrian.mobsters.exception.ActionFailedException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public void executeAction(WebClient webClient, AbstractAction currentAction) throws ActionFailedException {
        currentAction.setWebClient(webClient);
        setCurrentPage(currentAction);
        setAndExecuteCurrentAction(currentAction);

        for (int failureCount = 0; failureCount < MAX_ATTEMPTS; failureCount++) {
            setCurrentPage(currentAction);

            if (failureCount > 0) {
                humanBotService.randomSleep(1500 * (failureCount), 2300 * (failureCount));
            }

            if (currentAction.isFinished()) {
                finishAction(currentAction);
                return;
            }

            switch (failureCount) {
                case 3:
                    refresh(currentAction.getPage());
                case 7:
                    log.debug("Attempting to execute action: {} again.", currentAction.getName());
                    currentAction.run();
                    break;
            }
        }

        throw new ActionFailedException(currentAction);
    }

    private void refresh(HtmlPage page) {
        if (page != null) {
            Refresh refresh = (Refresh) actionService.getAction("Refresh");
            refresh.setPage(page);
            refresh.run();
            log.info("Attempting a page refresh.");
        }
    }

    private void finishAction(AbstractAction currentAction) {
        currentAction.response();

        log.debug("Action: {} finished.", currentAction.getName());
    }

    private void setAndExecuteCurrentAction(AbstractAction currentAction) throws ActionFailedException {
        //currentAction = mobster.getActions().peek();
        currentAction.setRunning(true);

        log.debug("Executing action: {}", currentAction.getName());

        currentAction.run();
    }

    private void setCurrentPage(AbstractAction currentAction) {
        Objects.requireNonNull(currentAction.getWebClient());
        List<WebWindow> windows = currentAction.getWebClient().getWebWindows();
        if (windows.size() < 2) {
//            currentAction.setPage((HtmlPage) windows.get(0).getEnclosedPage());
        } else {
            currentAction.setPage((HtmlPage) windows.get(1).getEnclosedPage());
        }
    }
}
