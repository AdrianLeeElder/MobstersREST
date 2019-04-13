package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mobsters")
@AllArgsConstructor
public class MobsterController {
    private MobsterRepository mobsterRepository;
    private ActionJobRepository actionJobRepository;

    @GetMapping
    public List<Mobster> getMobsters() {
        List<Mobster> mobsters = mobsterRepository.findAll();

        for (Mobster mobster : mobsters) {
            List<ActionJob> actionJobs = actionJobRepository
                    .findByMobsterUsername(mobster.getUsername());
            Mobster m = new Mobster(mobster.getId(), mobster.getUsername(), mobster.getPassword());
            m.setActionJobs(actionJobs);
        }

        return mobsters;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mobster saveMobster(@RequestBody Mobster mobster) {
        return mobsterRepository.save(mobster);
    }

    @GetMapping("{username}")
    public Mobster getMobsterByUserName(@PathVariable String username) {
        return mobsterRepository.findByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMobster(@PathVariable String id) {
        Optional<Mobster> mobsterOptional = mobsterRepository.findById(id);
        if (!mobsterOptional.isPresent()) {
            throw new MobsterNotFoundException(id);
        }
        mobsterRepository.deleteById(id);
    }
}
