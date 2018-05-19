package com.adrian.mobstersrest.mobsters.bean;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.adrian.mobstersrest.mobsters.repositories.DailyActionReactiveRepository;
import com.adrian.mobstersrest.mobsters.services.ActionFactoryService;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DailyActionsConfiguration {

  @Autowired
  private ActionFactoryService actionFactoryService;
  @Autowired
  private DailyActionReactiveRepository dailyActionReactiveRepository;

  @Bean
  @Scope(value = "prototype")
  public Queue<AbstractAction> dailyActions() {
    return dailyActionReactiveRepository
        .findAll()
        .map(action -> actionFactoryService.getAbstractAction(action.getActionName()))
        .collect(Collectors.toCollection(LinkedBlockingQueue::new))
        .block();
  }
}