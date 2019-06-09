package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.service.ActionJobCreator;
import com.adrian.mobsters.service.ActionJobService;
import com.adrian.mobsters.service.UserService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobControllerTest {
    private static final String API_BASE = "/api/v1/action-jobs";
    public static final String TRACY = "tracy";
    @Mock
    private ActionJobRepository actionJobRepository;
    @Mock
    private ActionJobService actionJobService;
    @Mock
    private ActionJobCreator actionJobCreator;
    @InjectMocks
    private ActionJobController actionJobController;
    @Mock
    private Principal principal;
    private MockMvc mockMvc;
    private Mobster mobster;
    private ActionJob actionJob;
    private DailyAction loginAction;

    @Before
    public void setup() {
        when(principal.getName()).thenReturn(TRACY);
        mockMvc = MockMvcBuilders.standaloneSetup(actionJobController).build();
        mobster = new Mobster("1", "zombie", "", TRACY);
        loginAction = new DailyAction("1", "LoginHtmlUnit", TRACY);

        actionJob = new ActionJob(mobster,
                Collections.singletonList(new Action(loginAction.getName())), true, false);
        actionJob.setId("1");
    }

    @Test
    public void createActionJobForMobster() throws Exception {
        List<ActionJob> actionJobs = Collections.singletonList(actionJob);
        given(actionJobCreator.getNewDailyActionJobs("zombie", TRACY)).willReturn(actionJobs);
        mockMvc.perform(get(API_BASE + "/new/zombie").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(actionJobs)))
                .andExpect(status().isCreated());
    }

    @Test
    public void viewJob() throws Exception {
        Optional<ActionJob> actionJobOptional = Optional.of(actionJob);
        given(actionJobRepository.findById("1")).willReturn(actionJobOptional);
        mockMvc
                .perform(get(API_BASE + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(actionJobOptional.get())))
                .andExpect(status().isOk());
    }

    @Test
    public void queueAll() throws Exception {
        List<ActionJob> actionJobs = Collections.singletonList(actionJob);
        given(actionJobCreator.getNewDailyJobForAllMobsters(TRACY)).willReturn(actionJobs);

        mockMvc
                .perform(get(API_BASE + "/queue-all")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(actionJobs)))
                .andExpect(status().isCreated());

    }
}
