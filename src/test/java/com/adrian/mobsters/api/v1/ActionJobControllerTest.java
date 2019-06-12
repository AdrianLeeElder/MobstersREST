package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.*;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.ActionTemplateRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import com.adrian.mobsters.service.ActionJobCreator;
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
import java.util.List;
import java.util.Optional;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobControllerTest {
    private static final String BASE_API = "/api/v1/action-jobs";
    private static final String TRACY = "tracy";
    private static final List<Action> ACTION_LIST = Collections.singletonList(Action.builder().name("200 Energy Link").build());
    private static final List<ActionTemplateAction> ACTION_LIST_TEMPLATE =
            Collections.singletonList(ActionTemplateAction.builder().name("200 Energy Link").build());
    private static final ActionTemplate ACTION_TEMPLATE = ActionTemplate.builder()
            .actions(ACTION_LIST_TEMPLATE).build();
    private static final Mobster MOBSTER = Mobster.builder().priority(1).build();
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private ActionJobRepository actionJobRepository;
    @Mock
    private ActionTemplateRepository actionTemplateRepository;
    @Mock
    private ActionJobCreator actionJobCreator;
    @Mock
    private Principal principal;
    @InjectMocks
    private ActionJobController actionJobController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(actionJobController).build();
        when(principal.getName()).thenReturn(TRACY);
    }

    @Test
    public void createFromTemplate() throws Exception {
        given(actionTemplateRepository.findByIdAndUser("1", TRACY)).willReturn(Optional.of(ACTION_TEMPLATE));
        given(mobsterRepository.findByUsernameRegexAndUser("bigtrac", TRACY))
                .willReturn(Collections.singletonList(MOBSTER));
        List<ActionJob> expectedJobs = Collections.singletonList(ActionJob
                .builder()
                .user(TRACY)
                .priority(1)
                .actionList(ACTION_LIST)
                .mobster(MOBSTER)
                .build());
        given(actionJobRepository.saveAll(anyIterable())).willReturn(expectedJobs);
        given(actionJobCreator.createFromTemplate(ACTION_TEMPLATE, Collections.singletonList(MOBSTER), principal.getName()))
                .willReturn(expectedJobs);

        mockMvc.perform(get(BASE_API + "/1/" + "bigtrac").principal(principal)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(expectedJobs)));
    }
}