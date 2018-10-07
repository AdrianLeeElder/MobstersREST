package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DailyActionJobInitializerImplTest {

    @Mock
    private ActionJobReactiveRepository actionJobReactiveRepository;
    @Mock
    private MobsterReactiveRepository mobsterReactiveRepository;
    @Mock
    private SmsService smsService;
    @Mock
    private ActionJobCreator actionJobCreator;
    @InjectMocks
    private DailyActionJobInitializerImpl dailyActionJobInitializer;

    @Test
    public void schedule() {
        given(actionJobReactiveRepository.deleteAll()).willReturn(Mono.empty());
        given(actionJobCreator.getNewDailyJobForAllMobsters())
                .willReturn(
                        Flux.fromIterable(
                                Arrays.asList(
                                        new ActionJob(null, null, true, true),
                                        new ActionJob(null, null, true, true)
                                )
                        )
                );
        dailyActionJobInitializer.schedule();

        verify(actionJobReactiveRepository).deleteAll();
        verify(actionJobCreator).getNewDailyJobForAllMobsters();
    }
}