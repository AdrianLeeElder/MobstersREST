package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.MobsterUsernameWrapper;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.service.ActionJobCreator;
import com.adrian.mobsters.service.ActionJobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/actionjob")
@AllArgsConstructor
public class ActionJobController {

    private final ActionJobReactiveRepository actionJobReactiveRepository;
    private final ActionJobCreator actionJobCreator;
    private ActionJobService actionJobService;

    @GetMapping("new/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ActionJob> addDailyJob(@PathVariable String username) {
        return actionJobCreator.getNewDailyActionJobs(username)
                .doOnNext(e -> actionJobService.run(e));
    }

    @GetMapping("queueall")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ActionJob> queueAll() {
        return actionJobCreator.getNewDailyJobForAllMobsters().doOnNext(e -> {
            actionJobService.run(e);
        });
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ActionJob> addDailyJobs(@RequestBody MobsterUsernameWrapper mobsterUsernameWrapper) {
        return actionJobCreator
                .getNewDailyActionJobs(mobsterUsernameWrapper.getUsernames())
                .doOnNext(e -> actionJobService.run(e));
    }

    @GetMapping("/{id}")
    public Mono<ActionJob> viewJob(@PathVariable String id) {
        return actionJobReactiveRepository.findById(id);
    }

    @GetMapping("/mobster/{username}")
    public Flux<ActionJob> getActionJobsUsername(@PathVariable String username) {
        return actionJobReactiveRepository.findByMobsterUsername(username);
    }

}
