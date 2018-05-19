package com.adrian.mobstersrest.mobsters.bootstrap;

import static java.util.stream.Collectors.toList;

import com.adrian.mobstersrest.mobsters.domain.DailyAction;
import com.adrian.mobstersrest.mobsters.repositories.DailyActionReactiveRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DailyActionsBootstrap implements CommandLineRunner {

  @Autowired
  private DailyActionReactiveRepository dailyActionReactiveRepository;

  @Override
  public void run(String... args) {
    if (dailyActionReactiveRepository.count().block() == 0) {
      String[] actions = new String[]{
          "Login",
          "200 Energy Link",
          "Daily Bonus Link",
          "My Mobster",
          "My Mob",
          "Accept All",
          "Optimizer Popup",
          "Optimizer",
          "Send All",
          "Collect All",
          "Optimizer Popup",
          "Optimizer",
          "Collect All",
          "Send All",
          "Bank",
          "Logout"
      };

      List<DailyAction> dailyActions = Arrays.stream(actions)
          .map(action -> new DailyAction(UUID.randomUUID().toString(), action))
          .collect(toList());

      dailyActionReactiveRepository.saveAll(dailyActions).blockFirst();
    }
  }
}
