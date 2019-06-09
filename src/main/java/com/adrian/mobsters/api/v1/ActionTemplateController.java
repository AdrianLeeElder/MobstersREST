package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.exception.ActionTemplateNotFoundException;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * A controller for interacting with {@link ActionTemplate}s.
 */
@RestController
@RequestMapping("/api/v1/action-templates")
@RequiredArgsConstructor
public class ActionTemplateController {
    private final ActionTemplateRepository actionTemplateRepository;

    @GetMapping
    public List<ActionTemplate> getUserTemplates(Principal principal) {
        return actionTemplateRepository.findAllByUser(principal.getName());
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTemplate(@RequestBody ActionTemplate actionTemplate) {
        actionTemplateRepository.save(actionTemplate);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ActionTemplate saveTemplate(@RequestBody ActionTemplate actionTemplate,
                                       Principal principal) {
        String id = actionTemplate.getId();

        if (id != null) {
            Optional<ActionTemplate> templateOpt = actionTemplateRepository.findByIdAndUser(id, principal.getName());

            if (templateOpt.isPresent()) {
                ActionTemplate template = templateOpt.get();
                String user = template.getUser();

                if (template.getUser().equalsIgnoreCase(user)) {
                    return actionTemplateRepository.save(templateOpt.get());
                }
            }
        }

        throw new ActionTemplateNotFoundException(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable String id, Principal principal) {
        Optional<ActionTemplate> actionTemplateOpt = actionTemplateRepository
                .findByIdAndUser(id, principal.getName());

        if (!actionTemplateOpt.isPresent()) {
            throw new ActionTemplateNotFoundException(id);
        }

        actionTemplateRepository.deleteById(id);
    }
}