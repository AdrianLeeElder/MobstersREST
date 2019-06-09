package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.repository.ActionJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActionJobScheduler {
    private static final long ACTION_JOB_RATE = 1000;
    @Autowired
    private ActionJobRepository actionJobRepository;
    @Autowired
    private ActionJobExecutor actionJobExecutor;

    @Scheduled(fixedDelay = ACTION_JOB_RATE)
    public void scheduleActionJobs() {
        log.trace("Scheduling action jobs...");

        for (ActionJob actionJob : getActionList()) {
            actionJobExecutor.run(actionJob);
        }
    }

    private List<ActionJob> getActionList() {
        return actionJobRepository
                .findAll()
                .stream()
                .filter(ActionJob::isIdle)
                .sorted(Comparator.comparingInt(ActionJob::getPriority)
                        .thenComparing(ActionJob::getCreatedDate))
                .collect(Collectors.toList());
    }
}
