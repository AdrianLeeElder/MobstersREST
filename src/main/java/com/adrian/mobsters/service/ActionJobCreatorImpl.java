package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.domain.Mobster;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActionJobCreatorImpl implements ActionJobCreator {
    @Override
    public List<ActionJob> createFromTemplate(ActionTemplate actionTemplate, List<Mobster> mobsters) {
        List<ActionJob> actionJobs = new ArrayList<>();

        for (Mobster mobster : mobsters) {
            ActionJob actionJob = ActionJob
                    .builder()
                    .mobster(mobster)
                    .priority(mobster.getPriority())
                    .createdDate(getDateTime())
                    .user(mobster.getUser())
                    .actionList(actionTemplate.getActionsList())
                    .build();

            actionJobs.add(actionJob);
        }

        return actionJobs;
    }

    protected LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
