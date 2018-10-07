package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.DailyActionJobAlreadyExistForMobster;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.DailyActionReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobCreatorTest {

    @Mock
    private ActionJobReactiveRepository actionJobReactiveRepository;
    @Mock
    private MobsterReactiveRepository mobsterReactiveRepository;
    @Mock
    private DailyActionReactiveRepository dailyActionReactiveRepository;
    @InjectMocks
    private ActionJobCreator actionJobCreator;
    private Mobster mobster;
    private List<DailyAction> dailyActionList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        mobster = new Mobster("1", "zombie", "");
        dailyActionList = Collections.singletonList(new DailyAction("Login"));

        given(mobsterReactiveRepository.findByUsernameRegex("zombie")).willReturn(Flux.just(mobster));
        given(dailyActionReactiveRepository.findAll()).willReturn(Flux.fromIterable(dailyActionList));
        given(actionJobReactiveRepository.findByDailyTrueAndMobsterUsernameRegex(anyString()))
                .willReturn(Flux.empty());
        given(actionJobReactiveRepository.saveAll(any(Iterable.class))).willAnswer(a -> Flux.fromIterable(
                a.getArgument(0)
        ));
    }

    @Test
    public void actionJobHasSuppliedMobsterUsername() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"))
                .collectList()
                .block();

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void whenNoMobsterExist() {
        given(mobsterReactiveRepository
                .findByUsernameRegex("johnes|zombie"))
                .willReturn(Flux.empty());

        expectedException.expectMessage("No mobsters matched the supplied username regex: johnes|zombie");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie")).subscribe();
    }

    @Test
    public void whenOnlyOneMobsterInTheListDoesntExist() {
        given(mobsterReactiveRepository.findByUsernameRegex("johnes|zombie"))
                .willReturn(Flux.fromIterable(Collections.singletonList(mobster)));

        expectedException.expectMessage("Mobster with username johnes not found.");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie")).subscribe();
    }

    @Test
    public void getAllDailyActionJobsForGivenListOfUsernames() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"))
                .collectList()
                .block();

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void getSingleActionJob() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"))
                .collectList()
                .block();

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void createActionJobForAllMobsters() {
        given(mobsterReactiveRepository.findAll()).willReturn(Flux.just(mobster));

        List<ActionJob> actual = actionJobCreator.getNewDailyJobForAllMobsters().collectList().block();

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    private ActionJob newActionJob(String actionJob) {
        return new ActionJob(mobster, Collections.singletonList(new Action(actionJob)), true, false);
    }

    private ActionJob emptyActionJobWithNoActions() {

        return new ActionJob(mobster, Collections.emptyList(), true, false);
    }
}
