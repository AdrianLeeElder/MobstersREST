package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActionTemplateSchedulerTest {
    private static final String TRACY = "TRACY";
    @Mock
    private ActionTemplateRepository actionTemplateRepository;
    @Mock
    private ActionJobCreator actionJobCreator;
    @Mock
    private ActionJobRepository actionJobRepository;
    @InjectMocks
    private ActionTemplateScheduler actionTemplateScheduler;

    @Test
    public void findsActionTemplateToSchedule() {
        List<Mobster> mobsters = Collections.singletonList(Mobster.builder().build());

        LocalDateTime now = LocalDateTime.now();

        ActionTemplate shouldRunTemplate = ActionTemplate
                .builder()
                .id("1")
                .frequency(Frequencies.DAILY)
                .mobsters(mobsters)
                .user(TRACY)
                .lastRan(now.minusDays(40))
                .build();
        ActionTemplate shouldNotRunTemplate = ActionTemplate
                .builder()
                .id("2")
                .frequency(Frequencies.DAILY)
                .lastRan(now)
                .mobsters(mobsters)
                .build();

        ActionTemplate nullLastRan = ActionTemplate
                .builder()
                .id("2")
                .frequency(Frequencies.DAILY)
                .mobsters(mobsters)
                .build();

        List<ActionTemplate> templates = Arrays.asList(shouldRunTemplate, shouldNotRunTemplate, nullLastRan);

        when(actionTemplateRepository.findAll()).thenReturn(templates);

        ActionJob createdJob = ActionJob
                .builder()
                .mobster(Mobster.builder().build())
                .build();

        List<ActionJob> jobs = Collections.singletonList(createdJob);
        when(actionJobCreator.create(shouldRunTemplate, TRACY)).thenReturn(jobs);

        actionTemplateScheduler.scan();

        verify(actionJobRepository, times(1)).saveAll(jobs);
        verify(actionJobCreator, times(0)).create(shouldNotRunTemplate, TRACY);
    }
}