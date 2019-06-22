package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActionJobCreatorImpl implements ActionJobCreator {
    @Override
    public List<ActionJob> create(ActionTemplate actionTemplate, String user) {
        List<ActionJob> actionJobs = new ArrayList<>();

        for (Mobster mobster : actionTemplate.getMobsters()) {
            ActionJob actionJob = ActionJob
                    .builder()
                    .mobster(mobster)
                    .priority(mobster.getPriority())
                    .createdDate(getDateTime())
                    .user(mobster.getUser())
                    .actionList(getActionListWithDefault(actionTemplate))
                    .user(user)
                    .template(actionTemplate)
                    .build();

            actionJobs.add(actionJob);
        }

        return actionJobs;
    }

    private List<Action> getActionListWithDefault(ActionTemplate actionTemplate) {
        ActionTemplateAction login = ActionTemplateAction.builder().name("Login").build();
        ActionTemplateAction logout = ActionTemplateAction.builder().name("Logout").build();

        List<ActionTemplateAction> resultList = new ArrayList<>();
        resultList.add(login);
        resultList.addAll(actionTemplate.getActions());
        resultList.add(logout);

        List<Action> actions = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            ActionTemplateAction templateAction = resultList.get(i);

            actions.add(Action
                    .builder()
                    .name(templateAction.getName())
                    .sequence(i)
                    .build());
        }

        return actions;
    }

    protected LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
