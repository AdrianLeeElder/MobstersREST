package com.adrian.mobstersrest.mobsters.actions;

import com.adrian.mobstersrest.mobsters.services.HumanBotService;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Adrian
 */
@Slf4j
@Getter
@Setter
@Service
class ActionExecutor {

  private static final int MAX_ATTEMPTS = 12;
  private String username;
  private HtmlPage currentPage;
  private AbstractAction currentAction;
  private String actionName;

  private MobsterService mobsterService;
  private WebClient webClient;
  private HumanBotService humanBotService;

  ActionExecutor(MobsterService mobsterService, WebClient webClient,
      HumanBotService humanBotService) {
    this.mobsterService = mobsterService;
    this.webClient = webClient;
    this.humanBotService = humanBotService;
  }

  boolean executeAction(AbstractAction currentAction) {
    this.currentAction = currentAction;
    setCurrentPage();
    setAndExecuteCurrentAction();

    for (int failureCount = 0; failureCount < MAX_ATTEMPTS; failureCount++) {
      setCurrentPage();

      if (failureCount > 0) {
        humanBotService.randomSleep(1500 * (failureCount), 2300 * (failureCount));
      }

      /*
      TODO: figure out how to not use mobster reference here
      if (currentAction instanceof WithdrawPopup && mobster.getBuyPropertyComplete()) {
      return true;
      }
      */

      if (this.currentAction.isFinished()) {
        finishAction();
        return true;
      }

      switch (failureCount) {
        case 3:
          //TODO: addQueuedAction("Refresh", mobster);
        case 7:
          log.debug("Attempting to execute action: {} again.", actionName);
          this.currentAction.run();
          break;
      }
    }

    return false;
  }

  private void finishAction() {
    currentAction.response();

    log.debug("Action: {} finished.", currentAction.getName());
  }

  private void setAndExecuteCurrentAction() {
    //currentAction = mobster.getActions().peek();
    currentAction.setPage(currentPage);
    currentAction.setRunning(true);
    actionName = currentAction.getName();

    log.debug("Executing action: {} for: {}", currentAction.getName(), username);

    currentAction.run();
  }

  private void setCurrentPage() {
    List<WebWindow> windows = webClient.getWebWindows();
    if (windows.size() < 2) {
      currentPage = (HtmlPage) windows.get(0).getEnclosedPage();
    } else {
      currentPage = (HtmlPage) windows.get(1).getEnclosedPage();
    }
  }
}
