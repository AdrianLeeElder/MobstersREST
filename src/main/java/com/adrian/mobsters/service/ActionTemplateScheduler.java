package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Finds {@link ActionTemplate}s that need to be scheduled and creates {@link ActionJob}s from it.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ActionTemplateScheduler {
    private static final long SCAN_RATE = 60000;
    private final ActionTemplateRepository actionTemplateRepository;
    private final ActionJobCreator actionJobCreator;
    private final ActionJobRepository actionJobRepository;

    /**
     * Scan the current {@link ActionTemplate}s in the database and see if any haven't been ran
     * since their scheduled frequency.
     */
    @Scheduled(fixedDelay = SCAN_RATE)
    public void scan() {
        log.trace("Scanning for ActionTemplates to schedule");

        List<ActionTemplate> templates = actionTemplateRepository.findAll();

        for (ActionTemplate template : templates) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastRan = template.getLastRan();

            if (lastRan == null ||
                    Duration.between(lastRan, now).toMillis() > Frequencies.toDuration(template.getFrequency())) {
                actionJobRepository.saveAll(actionJobCreator.create(template, template.getUser()));
                template.setLastRan(LocalDateTime.now());
                actionTemplateRepository.save(template);
            }
        }
    }
}
