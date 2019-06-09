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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobCreatorTest {
    public static final String TRACY = "tracy";
    @Mock
    private ActionJobRepository actionJobRepository;
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private DailyActionRepository dailyActionRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ActionJobCreator actionJobCreator;
    private Mobster mobster;
    private List<DailyAction> dailyActionList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        when(userService.getUser()).thenReturn("tracy");
        mobster = new Mobster("1", "zombie", "", TRACY);
        dailyActionList = Collections.singletonList(new DailyAction("2", "Login", TRACY));

        given(mobsterRepository.findByUsernameRegexAndUser("zombie", TRACY))
                .willReturn(Collections.singletonList(mobster));
        given(dailyActionRepository.findAllByUser(TRACY)).willReturn(dailyActionList);
        given(actionJobRepository.findByDailyTrueAndMobsterUsernameRegex(anyString()))
                .willReturn(Collections.emptyList());
        given(actionJobRepository.saveAll(any(Iterable.class))).willAnswer(a -> a.getArgument(0));
    }

    @Test
    public void actionJobHasSuppliedMobsterUsername() {
        List<ActionJob> actual = actionJobCreator.getNewDailyActionJobs(Collections.singletonList("zombie"), TRACY);

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void whenNoMobsterExist() {
        given(mobsterRepository
                .findByUsernameRegexAndUser("johnes|zombie", TRACY))
                .willReturn(Collections.emptyList());

        expectedException.expectMessage("No mobsters matched the supplied username regex: johnes|zombie");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie"), TRACY);
    }

    @Test
    public void whenOnlyOneMobsterInTheListDoesntExist() {
        given(mobsterRepository.findByUsernameRegexAndUser("johnes|zombie", TRACY))
                .willReturn(Collections.singletonList(mobster));

        expectedException.expectMessage("Mobster with username johnes not found.");

        actionJobCreator.getNewDailyActionJobs(Arrays.asList("johnes", "zombie"), TRACY);
    }

    @Test
    public void getAllDailyActionJobsForGivenListOfUsernames() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"), TRACY);

        ActionJob expected = newActionJob("Login");

        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    public void getSingleActionJob() {
        List<ActionJob> actual = actionJobCreator
                .getNewDailyActionJobs(Collections.singletonList("zombie"), TRACY);

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
        action.setQueued();
        return action;
    }

    private ActionJob emptyActionJobWithNoActions() {
        ActionJob actionJob = new ActionJob(mobster, Collections.emptyList(), true, false);
        actionJob.setQueued();
        return actionJob;
    }
}
