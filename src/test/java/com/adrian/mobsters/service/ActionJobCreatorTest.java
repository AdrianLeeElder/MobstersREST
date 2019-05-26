package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.DailyActionRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    private ActionJobRepository actionJobRepository;
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private DailyActionRepository dailyActionRepository;
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

        given(mobsterRepository.findByUsernameRegex("zombie")).willReturn(Collections.singletonList(mobster));
        given(dailyActionRepository.findAll()).willReturn(dailyActionList);
        given(actionJobRepository.findByDailyTrueAndMobsterUsernameRegex(anyString()))
                .willReturn(Collections.emptyList());
        given(actionJobRepository.saveAll(any(Iterable.class))).willAnswer(a -> a.getArgument(0));
    }

    @Test
    public void actionJobHasSuppliedMobsterUsername() {
        List<ActionJob> actual = actionJobCreator.getNewDailyActionJobs(Collections.singletonList("zombie"));

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void whenNoMobsterExist() {
        given(mobsterRepository
                .findByUsernameRegex("johnes|zombie"))
                .willReturn(Collections.emptyList());

        expectedException.expectMessage("No mobsters matched the supplied username regex: johnes|zombie");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie"));
    }

    @Test
    public void whenOnlyOneMobsterInTheListDoesntExist() {
        given(mobsterRepository.findByUsernameRegex("johnes|zombie"))
                .willReturn(Collections.singletonList(mobster));

        expectedException.expectMessage("Mobster with username johnes not found.");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie"));
    }

    @Test
    public void getAllDailyActionJobsForGivenListOfUsernames() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"));

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void getSingleActionJob() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"));

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void createActionJobForAllMobsters() {
        given(mobsterRepository.findAll()).willReturn(Collections.singletonList(mobster));

        List<ActionJob> actual = actionJobCreator.getNewDailyJobForAllMobsters();

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    private ActionJob newActionJob(String actionJob) {
        ActionJob action = new ActionJob(mobster, Collections.singletonList(new Action(actionJob)), true, false);
        action.setQueued(true);
        return action;
    }

    private ActionJob emptyActionJobWithNoActions() {
        ActionJob actionJob = new ActionJob(mobster, Collections.emptyList(), true, false);
        actionJob.setQueued(true);
        return actionJob;
    }
}
