package com.adrian.mobstersrest.mobsters.actions;

import com.adrian.mobstersrest.mobsters.exception.BotModeNotSupportedException;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Adrian
 */
@Slf4j
@Service
@Getter
@Setter
public class QueuedActionService {

  private Queue<AbstractAction> dailyActions;
  private ActionExecutor actionExecutor;
  private MobsterService mobsterService;
  private BotMode botMode;

  public QueuedActionService(
      Queue<AbstractAction> dailyActions,
      ActionExecutor actionExecutor,
      MobsterService mobsterService) {
    this.dailyActions = dailyActions;
    this.actionExecutor = actionExecutor;
    this.mobsterService = mobsterService;
  }

  private boolean hasActionAndActionExecutedSuccessfully() {
    return getActionsQueue().peek() != null && actionExecutor
        .executeAction(getActionsQueue().poll());
  }

  private Queue<AbstractAction> getActionsQueue() {
    if (botMode == BotMode.DAILY) {
      return dailyActions;
    }

    throw new BotModeNotSupportedException(botMode);
  }

  /**
   * Execute the current mobster's action queue.
   */
  public boolean executeQueuedActions(String username) {
    while (dailyActions.peek() != null) {
      if (!hasActionAndActionExecutedSuccessfully()) {
        return false;
      }
    }

    mobsterService.setComplete(true, username).block();

    return true;
  }
}
