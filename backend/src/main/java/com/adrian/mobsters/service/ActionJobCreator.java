package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.DailyActionJobAlreadyExistForMobster;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.exception.NoMobsterUsernamesMatchRegex;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.DailyActionRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import com.adrian.mobsters.repository.NoDailyActionsException;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ActionJobCreator {
    private final DailyActionRepository dailyActionRepository;
    private final MobsterRepository mobsterRepository;
    private final ActionJobRepository actionJobRepository;

    /**
     * Get a new {@link ActionJob} for each of the given {@link Mobster} usernames.
     *
     * @param usernames list of usernames to generate a new {@link ActionJob} for.
     * @return a list of {@link ActionJob}s for the given usernames.
     * @throws MobsterNotFoundException     for any mobster that is not found by the given username.
     * @throws NoMobsterUsernamesMatchRegex if there were no mobsters matching the generated regex. e.g (user1|user2)
     */
    public List<ActionJob> getNewDailyActionJobs(@NonNull List<String> usernames) {
        return getNewDailyActionJobs(String.join("|", usernames));
    }

    /**
     * Get a new {@link ActionJob} for each of the given {@link Mobster} usernames.
     *
     * @param usernameRegex regex to match on existing regexes.
     * @return a list of {@link ActionJob}s for the given usernames.
     * @throws MobsterNotFoundException     for any mobster that is not found by the given username.
     * @throws NoMobsterUsernamesMatchRegex if there were no mobsters matching the generated regex. e.g (user1|user2)
     */
    public List<ActionJob> getNewDailyActionJobs(String usernameRegex) {
        List<Mobster> mobsters = mobsterRepository.findByUsernameRegex(usernameRegex);
        if (mobsters.isEmpty()) {
            throw new NoMobsterUsernamesMatchRegex(usernameRegex);
        }

        validateAllMobstersFound(mobsters, usernameRegex);
        verifyNoDailyActionJobsExistWithUsernames(mobsters, usernameRegex);

        List<DailyAction> dailyActions = dailyActionRepository.findAll();

        if (dailyActions.isEmpty()) {
            throw new NoDailyActionsException();
        }

        List<ActionJob> actions = mobsters
                .stream()
                .map(m -> {
                    ActionJob actionJob = new ActionJob(m,
                            getActionListFromDailyActions(dailyActions), true, false);

                    actionJob.setQueued(true);

                    return actionJob;
                }).collect(toList());

        actionJobRepository.saveAll(actions);

        return actions;
    }

    private void validateAllMobstersFound(List<Mobster> mobsters, String usernameRegex) {
        String[] usernames = usernameRegex.split("\\|");

        for (String user : usernames) {
            Optional<Mobster> mobsterOptional =
                    mobsters
                            .stream()
                            .filter(m -> m.getUsername().equals(user))
                            .findFirst();

            if (!mobsterOptional.isPresent()) {
                throw new MobsterNotFoundException(user);
            }
        }
    }

    private void verifyNoDailyActionJobsExistWithUsernames(List<Mobster> mobsters, String usernameRegex) {
        List<ActionJob> actionJobs = actionJobRepository.findByDailyTrueAndMobsterUsernameRegex(usernameRegex);
        if (!actionJobs.isEmpty()) {
            throw new DailyActionJobAlreadyExistForMobster(usernameRegex);
        }
    }

    private List<Action> getActionListFromDailyActions(@NonNull List<DailyAction> aL) {
        return aL
                .stream()
                .map(action -> new Action(action.getName()))
                .collect(toList());
    }

    public List<ActionJob> getNewDailyJobForAllMobsters() {
        List<Mobster> mobsters = Lists.newArrayList(mobsterRepository.findAll());

        return getNewDailyActionJobs(mobsters
                .stream()
                .map(Mobster::getUsername)
                .collect(Collectors.toList()));
    }
}
