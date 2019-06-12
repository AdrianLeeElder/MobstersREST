package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.StatusConstants;
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
    private static final long ACTION_JOB_RATE = 30000;
    @Autowired
    private ActionJobRepository actionJobRepository;
    @Autowired
    private ActionJobExecutor actionJobExecutor;

    @Scheduled(fixedDelay = ACTION_JOB_RATE)
    public void scheduleActionJobs() {
        if (!Boolean.valueOf(System.getProperty("proxies.loaded", "false"))) {
            log.debug("Proxies not loaded, waiting to schedule action jobs");
            return;
        }

        log.trace("Scheduling action jobs...");

        List<ActionJob> jobs = getActionList();
        for (ActionJob actionJob : jobs) {
            log.trace("Sending action job to scheduler: {}", actionJob);
            actionJob.setQueued();
            actionJobExecutor.run(actionJob);
        }

        actionJobRepository.saveAll(jobs);
    }

    private List<ActionJob> getActionList() {
        List<ActionJob> actionJobs = actionJobRepository
                .findAllByStatus(StatusConstants.IDLE)
                .stream()
                .sorted(Comparator.comparingInt(ActionJob::getPriority)
                        .thenComparing(ActionJob::getCreatedDate))
                .collect(Collectors.toList());

        log.debug("Found these action jobs: {}", actionJobs);
        return actionJobs;
    }
}
