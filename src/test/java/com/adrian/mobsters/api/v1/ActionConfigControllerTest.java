package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionConfig;
import com.adrian.mobsters.repository.ActionConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class ActionConfigControllerTest {
    private static final String BASE_API = "/api/v1/action-configs";
    private static final String ENERGY_LINK = "200 Energy Link";
    @Mock
    private ActionConfigRepository actionConfigRepository;
    @InjectMocks
    private ActionConfigController actionConfigController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(actionConfigController).build();
    }

    @Test
    public void getActionConfigs() throws Exception {
        when(actionConfigRepository.findAll())
                .thenReturn(Collections.singletonList(ActionConfig
                        .builder()
                        .name(ENERGY_LINK)
                        .build()));

        mockMvc
                .perform(get(BASE_API))
                .andDo(print())
                .andExpect(jsonPath("$.[0]name", is(equalTo(ENERGY_LINK))));
    }
}