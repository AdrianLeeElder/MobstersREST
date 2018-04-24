package com.adrian.mobstersrest.mobsters.controller;

import com.adrian.mobstersrest.mobsters.api.v1.controller.MobsterController;
import com.adrian.mobstersrest.mobsters.model.MobsterDto;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class MobsterControllerTest {

    @Mock
    private MobsterService mobsterService;

    @InjectMocks
    private MobsterController mobsterController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mobsterController).build();
    }

    @Test
    public void getAllMobsters() throws Exception {
        MobsterDto mobster = new MobsterDto();
        mobster.setUsername("johnny");

        when(mobsterService.getMobsters()).thenReturn(Collections.singletonList(mobster));

        mockMvc.perform(
                get("/api/v1/mobsters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[:1]",
                        hasSize(1))
                );
    }

    @Test
    public void createNewMobster() throws Exception {
        mockMvc
                .perform(post("/api/v1/mobsters/zombie")
                        .contentType(MediaType.APPLICATION_JSON))
        .andExpect();

    }
}
