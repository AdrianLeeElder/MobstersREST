package com.adrian.mobstersrest.mobsters.api.v1.controller;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/mobsters")
@AllArgsConstructor
public class MobsterController {

    private MobsterService mobsterService;

    @GetMapping
    public Flux<Mobster> getMobsters() {
        return mobsterService.getMobsters();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher<Void> addMobster(@RequestBody Publisher<Mobster> mobster) {
        return mobsterService.createMobsters(mobster).then();
    }

    @PostMapping("{username}/queue")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Publisher<Void> queue(@PathVariable String username) {
        return mobsterService.addToQueue(username);
    }
}
