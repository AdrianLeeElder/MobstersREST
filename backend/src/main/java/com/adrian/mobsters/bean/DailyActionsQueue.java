package com.adrian.mobsters.bean;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.service.ActionFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class DailyActionsQueue {

    @Autowired
    private ActionFactoryService actionFactoryService;

    @Bean
    @Scope(value = "prototype")
    public Queue<AbstractAction> dailyActions() {
        String[] dailyActions = new String[]{
                "LoginHtmlUnit", "Logout"
        };

        List<AbstractAction> actions = new ArrayList<>();
        Arrays.stream(dailyActions).forEach(action ->
                actions.add(actionFactoryService.getAbstractAction(action)));

        return new LinkedBlockingQueue<>(actions);
    }
}
