package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.MobsterUsernameWrapper;
import com.adrian.mobsters.exception.ActionJobNotFound;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.service.ActionJobCreator;
import com.adrian.mobsters.service.ActionJobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/action-jobs")
@AllArgsConstructor
public class ActionJobController {
    private final ActionJobRepository actionJobRepository;
    private final ActionJobCreator actionJobCreator;
    private ActionJobService actionJobService;

    @GetMapping("new/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> addDailyJob(@PathVariable String username) {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyActionJobs(username);

        for (ActionJob actionJob : actionJobs) {
            actionJobService.run(actionJob);
        }

        return actionJobs;
    }

    @GetMapping("queue-all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> queueAll() {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyJobForAllMobsters();
        for (ActionJob actionJob : actionJobs) {
            actionJobService.run(actionJob);
        }

        return actionJobs;
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> addDailyJobs(@RequestBody MobsterUsernameWrapper mobsterUsernameWrapper) {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyActionJobs(mobsterUsernameWrapper.getUsernames());

        for (ActionJob actionJob : actionJobs) {
            actionJobService.run(actionJob);
        }

        return actionJobs;
    }

    @GetMapping("/{id}")
    public ActionJob viewJob(@PathVariable String id) {
        Optional<ActionJob> actionJobOptional = actionJobRepository.findById(id);

        if (actionJobOptional.isPresent()) {
            return actionJobOptional.get();
        }

        throw new ActionJobNotFound(id);
    }

    @GetMapping("/mobster/{username}")
    public List<ActionJob> getActionJobsUsername(@PathVariable String username) {
        return actionJobRepository.findByMobsterUsername(username);
    }
}
