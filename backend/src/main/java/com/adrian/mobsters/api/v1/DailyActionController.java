package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.DailyActionContainer;
import com.adrian.mobsters.repository.DailyActionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/dailyaction")
@RequiredArgsConstructor
public class DailyActionController {

    private final DailyActionReactiveRepository dailyActionReactiveRepository;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("add")
    public Mono<DailyAction> newAction(@RequestBody DailyAction dailyAction) {
        return dailyActionReactiveRepository.save(dailyAction);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("addlist")
    public Flux<DailyAction> addList(@RequestBody DailyActionContainer dailyActionContainer) {
        return dailyActionReactiveRepository.saveAll(dailyActionContainer.getDailyActions());
    }
}
