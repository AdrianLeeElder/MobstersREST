package com.adrian.mobstersrest.mobsters.api.v1.controller;

import com.adrian.mobstersrest.mobsters.model.MobsterDto;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mobsters")
@AllArgsConstructor
public class MobsterController {

    private MobsterService mobsterService;

    @GetMapping
    public ResponseEntity<List<MobsterDto>> getMobsters() {
        return new ResponseEntity<>(mobsterService.getMobsters(), HttpStatus.OK);
    }

    @PostMapping("{username}")
    public ResponseEntity<MobsterDto> addMobster(@PathVariable String username) {
        return new ResponseEntity<>(mobsterService.createMobster(username), HttpStatus.CREATED);
    }
}
