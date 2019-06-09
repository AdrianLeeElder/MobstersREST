package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import com.adrian.mobsters.service.MobsterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MobsterControllerTest {
    private static final String BASE_API = "/api/v1/mobsters";
    private static final String TRACY = "tracy";
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private MobsterService mobsterService;
    @Mock
    private ActionJobRepository actionJobRepository;
    @Mock
    private Principal principal;
    @InjectMocks
    private MobsterController mobsterController;
    private List<ActionJob> actionJobList;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mobsterController).build();
        when(principal.getName()).thenReturn(TRACY);
    }

    @Test
    public void getAllMobsters() throws Exception {
        actionJobList = Collections.singletonList(ActionJob.builder().build());
        Mobster mobster = Mobster.builder().id("1").username("Zombie").user(TRACY).build();
        List<Mobster> mobsters = Collections.singletonList(mobster);
        given(mobsterService.getMobsters(eq(TRACY), any(PageRequest.class))).willReturn(mobsters);

        mockMvc.perform(get("/api/v1/mobsters?&pageNumber=0").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mobsters)))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewMobster() throws Exception {
        Mobster mobster = Mobster.builder().id("1").username("john").user(TRACY).build();
        mockMvc.perform(post("/api/v1/mobsters", mobster)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mobster)));
    }

    @Test
    public void getTotalPages() throws Exception {
        given(mobsterRepository.findAllByUser(TRACY))
                .willReturn(Collections.singletonList(Mobster.builder().build()));
        mockMvc.perform(get(BASE_API + "/total-pages").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMobster() throws Exception {
        Mobster toDelete = Mobster.builder().id("1").user(TRACY).username("bigtrac").build();
        when(mobsterRepository.findAllByIdAndUser("1", TRACY)).thenReturn(Optional.of(toDelete));

        mockMvc.perform(delete(BASE_API + "/delete/1").principal(principal))
                .andExpect(status().isAccepted());
    }
}
