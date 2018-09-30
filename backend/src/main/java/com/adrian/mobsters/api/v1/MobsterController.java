package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import com.adrian.mobsters.service.MobsterService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/mobster")
@AllArgsConstructor
public class MobsterController {

    private MobsterReactiveRepository mobsterReactiveRepository;
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

    @GetMapping("{username}")
    public Mono<Mobster> getMobsterByUserName(@PathVariable String username) {
        return mobsterReactiveRepository.findByUsername(username);
    }
}
