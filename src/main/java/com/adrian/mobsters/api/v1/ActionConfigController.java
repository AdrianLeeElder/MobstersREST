package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionConfig;
import com.adrian.mobsters.repository.ActionConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/action-configs")
@RequiredArgsConstructor
public class ActionConfigController {
    private final ActionConfigRepository actionConfigRepository;

    @GetMapping
    public List<ActionConfig> getActionConfigs() {
        return actionConfigRepository.findAll();
    }
}
