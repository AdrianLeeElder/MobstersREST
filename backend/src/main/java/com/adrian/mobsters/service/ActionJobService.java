package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ActionJobService {
    void run(ActionJob actionJob);
}
