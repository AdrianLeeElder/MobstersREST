package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.repository.ActionJobRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DailyActionJobInitializerImplTest {
    @Mock
    private ActionJobRepository actionJobRepository;
    @Mock
    private ActionJobCreator actionJobCreator;
    @Mock
    private SmsService smsService;
    @InjectMocks
    private DailyActionJobInitializerImpl dailyActionJobInitializer;

    @Test
    public void schedule() {
        given(actionJobCreator.getNewDailyJobForAllMobsters()).willReturn(Arrays.asList(
                new ActionJob(null, null, true, true),
                new ActionJob(null, null, true, true)
        ));

        dailyActionJobInitializer.schedule();

        verify(actionJobRepository).deleteAll();
        verify(actionJobCreator).getNewDailyJobForAllMobsters();
    }
}