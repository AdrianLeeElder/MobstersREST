package com.adrian.mobstersrest.mobsters.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@AllArgsConstructor
@Service
public class MobsterServiceImpl implements MobsterService {

    private VaultTemplate vaultTemplate;

    @Override
    public String retrieveMobsterPassword(String username) {
        VaultResponse vaultResponse = vaultTemplate.read("secret/mobsters");

        return (String) vaultResponse.getData().get(username);
    }
}
