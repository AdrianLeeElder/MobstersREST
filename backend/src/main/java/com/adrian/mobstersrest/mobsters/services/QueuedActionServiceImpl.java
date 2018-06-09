package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.adrian.mobstersrest.mobsters.actions.BotMode;
import com.adrian.mobstersrest.mobsters.exception.BotModeNotSupportedException;
import com.adrian.mobstersrest.mobsters.repositories.DailyActionReactiveRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Adrian
 */
@Slf4j
@Service
@Getter
@Setter
public class QueuedActionServiceImpl implements QueuedActionService {

  @Autowired
  private DailyActionReactiveRepository dailyActionReactiveRepository;
  @Autowired
  private Queue<AbstractAction> dailyActions;
  private ActionExecutor actionExecutor;
  private MobsterService mobsterService;
  private BotMode botMode;

  public QueuedActionServiceImpl(
      ActionExecutor actionExecutor,
      MobsterService mobsterService) {
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
  @Override
  public boolean executeQueuedActions(String username) {
    while (dailyActions.peek() != null) {
      if (!hasActionAndActionExecutedSuccessfully()) {
        return false;
      }
    }

    mobsterService.setComplete(true, username).block();

    return true;
  }

  @Override
  public Queue<AbstractAction> getDailyActions() {
    return dailyActions;
  }
}
