package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.mapper.MobsterMapper;
import com.adrian.mobstersrest.mobsters.model.MobsterDto;
import com.adrian.mobstersrest.mobsters.repositories.MobsterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class MobsterServiceImpl implements MobsterService {

    private VaultTemplate vaultTemplate;
    private MobsterRepository mobsterRepository;
    private MobsterMapper mobsterMapper;

    @Override
    public String retrieveMobsterPassword(String username) {
        VaultResponse vaultResponse = vaultTemplate.read("secret/mobsters");

        return (String) vaultResponse.getData().get(username);
    }

    @Override
    public List<MobsterDto> getMobsters() {
        return mobsterRepository
                .findAll()
                .stream()
                .map(mobsterMapper::mobsterToMobsterDto)
                .collect(toList());
    }

    @Override
    public MobsterDto createMobster(String username) {
        Mobster mobster = new Mobster();
        mobster.setUsername(username);

        return mobsterMapper.mobsterToMobsterDto(mobster);
    }
}
