package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.adrian.mobstersrest.mobsters.actions.BotMode;
import java.util.Queue;

public interface QueuedActionService {

  boolean executeQueuedActions(String username);

  void setBotMode(BotMode daily);

  Queue<AbstractAction> getDailyActions();
}
