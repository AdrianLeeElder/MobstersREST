package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import com.adrian.mobsters.service.MobsterService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/mobster")
@AllArgsConstructor
public class MobsterController {

    private MobsterReactiveRepository mobsterReactiveRepository;
    private MobsterService mobsterService;
    private ActionJobReactiveRepository actionJobReactiveRepository;

    @GetMapping
    public Flux<Mobster> getMobsters() {
        return mobsterReactiveRepository
                .findAll()
                .flatMap(mobster -> actionJobReactiveRepository
                        .findByMobsterUsername(mobster.getUsername())
                        .collectList()
                        .map(actionJobList -> {
                            Mobster m = new Mobster(mobster.getId(), mobster.getUsername(), mobster.getPassword());
                            m.setActionJobs(actionJobList);

                            return m;
                        })
                );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Mobster> saveMobster(@RequestBody Mobster mobster) {
        return mobsterReactiveRepository.save(mobster);
    }

    @GetMapping("{username}")
    public Mono<Mobster> getMobsterByUserName(@PathVariable String username) {
        return mobsterReactiveRepository.findByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteMobster(@PathVariable String id) {
        return mobsterReactiveRepository.deleteById(id);
    }
}
