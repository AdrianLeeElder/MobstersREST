package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ActionTemplateControllerTest {
    private static final String TRACY = "tracy";
    private static final String BASE_API = "/api/v1/action-templates";
    @Mock
    private ActionTemplateRepository actionTemplateRepository;
    @Mock
    private Principal principal;
    @InjectMocks
    private ActionTemplateController actionTemplateController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(actionTemplateController).build();
        when(principal.getName()).thenReturn(TRACY);
    }

    @Test
    public void getUserTemplates() throws Exception {
        when(actionTemplateRepository.findAllByUser(TRACY))
                .thenReturn(Collections.singletonList(ActionTemplate
                        .builder()
                        .user(TRACY)
                        .build()));

        mockMvc
                .perform(get(BASE_API).principal(principal))
                .andDo(print())
                .andExpect(jsonPath("$.[0]user", is(equalTo(TRACY))));
    }

    @Test
    public void addTemplate() throws Exception {
        mockMvc
                .perform(post(BASE_API + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ActionTemplate.builder().build())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTemplate() throws Exception {
        when(actionTemplateRepository.findByIdAndUser("1", TRACY))
                .thenReturn(Optional.of(ActionTemplate
                        .builder()
                        .user(TRACY)
                        .build()));
        mockMvc
                .perform(delete(BASE_API + "/1").principal(principal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTemplateNotFound() throws Exception {
        when(actionTemplateRepository.findByIdAndUser("1", TRACY))
                .thenReturn(Optional.empty());
        mockMvc
                .perform(delete(BASE_API + "/1").principal(principal))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void saveTemplate() throws Exception {
        ActionTemplate template = ActionTemplate.builder().id("1").user(TRACY).build();
        when(actionTemplateRepository.findByIdAndUser("1", TRACY))
                .thenReturn(Optional.of(template));

        mockMvc
                .perform(post(BASE_API + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(template)).principal(principal))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void saveTemplateNotFound() throws Exception {
        ActionTemplate template = ActionTemplate.builder().id("1").user(TRACY).build();
        when(actionTemplateRepository.findByIdAndUser("1", TRACY))
                .thenReturn(Optional.empty());

        mockMvc
                .perform(post(BASE_API + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(template)).principal(principal))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
