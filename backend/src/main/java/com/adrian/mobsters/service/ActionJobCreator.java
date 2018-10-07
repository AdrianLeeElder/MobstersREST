package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.DailyActionJobAlreadyExistForMobster;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.exception.NoMobsterUsernamesMatchRegex;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.DailyActionReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import com.adrian.mobsters.repository.NoDailyActionsException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ActionJobCreator {

    private final DailyActionReactiveRepository dailyActionReactiveRepository;
    private final MobsterReactiveRepository mobsterReactiveRepository;
    private final ActionJobReactiveRepository actionJobReactiveRepository;

    /**
     * Get a new {@link ActionJob} for each of the given {@link Mobster} usernames.
     *
     * @param usernames list of usernames to generate a new {@link ActionJob} for.
     * @return a list of {@link ActionJob}s for the given usernames.
     * @throws MobsterNotFoundException     for any mobster that is not found by the given username.
     * @throws NoMobsterUsernamesMatchRegex if there were no mobsters matching the generated regex. e.g (user1|user2)
     */
    public Flux<ActionJob> getNewDailyActionJobs(@NonNull List<String> usernames) {
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
    public Flux<ActionJob> getNewDailyActionJobs(String usernameRegex) {
        return mobsterReactiveRepository
                .findByUsernameRegex(usernameRegex)
                .switchIfEmpty(Mono.error(new NoMobsterUsernamesMatchRegex(usernameRegex)))
                .collectList()
                .doOnNext(monoListMobsters -> validateAllMobstersFound(monoListMobsters, usernameRegex))
                .doOnNext(monoListMobsters -> verifyNoDailyActionJobsExistWithUsernames(monoListMobsters, usernameRegex))
                .flatMapMany(mobsters ->
                        dailyActionReactiveRepository
                                .findAll()
                                .collectList()
                                .switchIfEmpty(Mono.error(new NoDailyActionsException()))
                                .flatMapMany(actions ->
                                        actionJobReactiveRepository.saveAll(
                                                mobsters
                                                        .stream()
                                                        .map(m -> {
                                                            ActionJob actionJob = new ActionJob(m,
                                                                    getActionListFromDailyActions(actions),
                                                                    true,
                                                                    false);

                                                            actionJob.setQueued(true);

                                                            return actionJob;
                                                        })
                                                        .collect(toList())
                                        )

                                )
                );
    }

    private Mono<List<Mobster>> validateAllMobstersFound(List<Mobster> monoListMobsters, String usernameRegex) {
        String[] usernames = usernameRegex.split("\\|");

        for (String user : usernames) {
            Optional<Mobster> mobsterOptional =
                    monoListMobsters
                            .stream()
                            .filter(m -> m.getUsername().equals(user))
                            .findFirst();

            if (!mobsterOptional.isPresent()) {
                throw new MobsterNotFoundException(user);
            }
        }

        return Mono.just(monoListMobsters);
    }

    private Mono<List<Mobster>> verifyNoDailyActionJobsExistWithUsernames(List<Mobster> mobsters, String usernameRegex) {
        return actionJobReactiveRepository
                .findByDailyTrueAndMobsterUsernameRegex(usernameRegex)
                .collectList()
                .flatMap(e -> {
                    System.out.println("Ran");
                    return Mono.error(new DailyActionJobAlreadyExistForMobster(""));
                })
                .flatMap(e -> Mono.just(mobsters));
    }

    private List<Action> getActionListFromDailyActions(@NonNull List<DailyAction> aL) {
        return aL
                .stream()
                .map(action -> new Action(action.getName()))
                .collect(toList());
    }

    public Flux<ActionJob> getNewDailyJobForAllMobsters() {
        return mobsterReactiveRepository
                .findAll()
                .collectList()
                .flatMapMany(mobsterList ->
                        getNewDailyActionJobs(mobsterList
                                .stream()
                                .map(Mobster::getUsername)
                                .collect(Collectors.toList())));
    }
}
