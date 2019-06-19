package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.domain.MobsterWrapper;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MobsterServiceImplTest {
    public static final String TRACY = "tracy";
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private ActionJobRepository actionJobRepository;
    @InjectMocks
    private MobsterServiceImpl mobsterServiceImpl;

    private String JOHN_SMITH = "John Smith";

    @Test
    public void readUserPassword() {
        Mobster mobster = Mobster.builder().id("1").user("zombie").password("hax").user("tracy").build();
        given(mobsterRepository.findByUsername("zombie")).willReturn(Optional.of(mobster));

        assertThat(mobsterServiceImpl.retrieveMobsterPassword("zombie"), equalTo("hax"));
    }

    @Test
    public void createMobsters() {
        Mobster mobster = Mobster.builder().id("1").username(JOHN_SMITH).user("tracy").build();
        MobsterWrapper mobsterWrapper = new MobsterWrapper(Collections.singletonList(mobster));
        given(mobsterRepository.saveAll(anyCollection())).willReturn(mobsterWrapper.getMobsters());

        List<Mobster> createdMobster = mobsterServiceImpl.createMobsters(Collections.singletonList(mobster));

        assertThat(createdMobster, is(equalTo(mobsterWrapper.getMobsters())));
    }

    @Test
    public void getMobstersAndStatusSetToComplete() {
        Mobster mobster = Mobster.builder().id("1").build();
        testReturnsMobsterStatus("complete", ActionJob
                .builder()
                .actionList(Collections.singletonList(Action.builder()
                        .name("login")
                        .build()))
                .status("complete")
                .mobster(mobster)
                .build());
    }

    @Test
    public void getMobstersAndStatusSetToIdle() {
        Mobster mobster = Mobster.builder().id("1").build();
        testReturnsMobsterStatus("idle", ActionJob
                .builder()
                .actionList(Collections.singletonList(Action.builder()
                        .name("login")
                        .build()))
                .status("idle")
                .mobster(mobster)
                .build());
    }

    @Test
    public void testStatusWithOneRunningAndRestComplete() {
        Mobster mobster = Mobster.builder().id("1").build();
        testReturnsMobsterStatus("running", ActionJob
                        .builder()
                        .actionList(Collections.singletonList(Action.builder()
                                .name("login")
                                .build()))
                        .status("running")
                        .mobster(mobster)
                        .build(),
                ActionJob
                        .builder()
                        .actionList(Collections.singletonList(Action.builder()
                                .name("login")
                                .build()))
                        .status("complete")
                        .mobster(mobster)
                        .build());
    }

    private void testReturnsMobsterStatus(String returnsStatus, ActionJob... actionJobs) {
        given(actionJobRepository.findByMobster_IdIn(Collections.singletonList("1")))
                .willReturn(Arrays.asList(actionJobs));
        given(mobsterRepository.findAllByUser(eq(TRACY), any(PageRequest.class)))
                .willReturn(new PageImpl<>(Collections.singletonList(Mobster
                        .builder()
                        .id("1")
                        .build())));
        List<Mobster> mobsters = mobsterServiceImpl.getMobsters(TRACY, PageRequest.of(0, 10));

        assertThat(mobsters, is(equalTo(Collections.singletonList(Mobster
                .builder()
                .id("1")
                .actionJobStatus(returnsStatus).build()))));
    }
}
