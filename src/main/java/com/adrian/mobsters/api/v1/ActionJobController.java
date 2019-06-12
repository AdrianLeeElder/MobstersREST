package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.exception.ActionTemplateNotFoundException;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import com.adrian.mobsters.service.ActionJobCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/action-jobs")
public class ActionJobController {
    private final ActionTemplateRepository actionTemplateRepository;
    private final ActionJobCreator actionJobCreator;
    private final ActionJobRepository actionJobRepository;
    private final MobsterRepository mobsterRepository;

    /**
     * Create a {@link ActionJob} by the given {@link ActionTemplate}.
     *
     * @param id           the {@link ActionTemplate} identifier.
     * @param mobsterNames the list of mobster usernames.
     * @param principal    the authorized user.
     * @return
     */
    @GetMapping(value = "/{id}/{mobsterNames}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ActionJob> createFromTemplate(@PathVariable String id,
                                              @PathVariable List<String> mobsterNames,
                                              Principal principal) {
        Optional<ActionTemplate> actionTemplateOpt = actionTemplateRepository.findByIdAndUser(id, principal.getName());

        if (!actionTemplateOpt.isPresent()) {
            throw new ActionTemplateNotFoundException(id);
        }

        List<ActionJob> actionJobs = actionJobCreator.createFromTemplate(actionTemplateOpt.get(),
                mobsterRepository.findByUsernameRegexAndUser(getRegex(mobsterNames), principal.getName()),
                principal.getName());

        return actionJobRepository.saveAll(actionJobs);
    }

    @GetMapping("/mobster/{mobsterId}/{pageNumber}/{pageSize}")
    public Page<ActionJob> getActionJobsForMobster(@PathVariable String mobsterId,
                                                   @PathVariable int pageNumber,
                                                   @PathVariable int pageSize,
                                                   Principal principal) {
        return actionJobRepository
                .findByUserAndMobster_Id(principal.getName(), mobsterId,
                        PageRequest.of(pageNumber,
                                pageSize,

                                Sort.by("createdDate")));
    }

    private String getRegex(List<String> mobsterNames) {
        return mobsterNames
                .stream().collect(Collectors.joining("|", "^(", ")$"));
    }
}
