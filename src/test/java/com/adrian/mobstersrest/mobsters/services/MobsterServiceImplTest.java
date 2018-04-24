package com.adrian.mobstersrest.mobsters.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MobsterServiceImplTest {

    @Mock
    private VaultTemplate vaultTemplate;
    @Mock
    private VaultResponse vaultResponse;

    @InjectMocks
    private MobsterServiceImpl mobsterServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readUserPassword() {
        when(vaultTemplate.read(anyString())).thenReturn(vaultResponse);
        when(vaultResponse.getData()).thenReturn(Collections.singletonMap("bob", "hax"));

        assertThat(mobsterServiceImpl.retrieveMobsterPassword("bob"), equalTo("hax"));
    }
}
