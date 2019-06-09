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

import java.security.Principal;
import java.util.Collections;
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
    public List<ActionJob> addDailyJob(@PathVariable String username, Principal principal) {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyActionJobs(username, principal.getName());

        for (ActionJob actionJob : actionJobs) {
            actionJobService.run(actionJob);
        }

        return actionJobs;
    }

    @GetMapping("queue-all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> queueAll(Principal principal) {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyJobForAllMobsters(principal.getName());
        for (ActionJob actionJob : actionJobs) {
            actionJobService.run(actionJob);
        }

        return actionJobs;
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> addDailyJobs(@RequestBody MobsterUsernameWrapper mobsterUsernameWrapper, Principal principal) {
        List<ActionJob> actionJobs = actionJobCreator.getNewDailyActionJobs(mobsterUsernameWrapper.getUsernames(),
                principal.getName());

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

    @GetMapping("/{username}/{limit}")
    public List<ActionJob> getActionJobsUsername(@PathVariable String username, @PathVariable int limit) {
        List<ActionJob> actionJobs = actionJobRepository.findByMobsterUsername(username);
        if (actionJobs == null || actionJobs.isEmpty()) {
            return Collections.emptyList();
        }

        return actionJobs.subList(0, limit);
    }
}
