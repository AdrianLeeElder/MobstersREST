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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MobsterControllerTest {
    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private MobsterService mobsterService;
    @Mock
    private ActionJobRepository actionJobRepository;
    @InjectMocks
    private MobsterController mobsterController;
    private List<ActionJob> actionJobList;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mobsterController).build();
    }

    @Test
    public void getAllMobsters() throws Exception {
        actionJobList = Collections.singletonList(new ActionJob(null, null, true, false));
        Mobster mobster = new Mobster("1", "Zombie", "");

        List<Mobster> mobsters = Collections.singletonList(mobster);
        given(mobsterService.getMobsters(any(PageRequest.class))).willReturn(mobsters);
        given(actionJobRepository.findByMobsterUsername("Zombie")).willReturn(actionJobList);

        mockMvc.perform(get("/api/v1/mobsters?&pageNumber=0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mobsters)))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewMobster() throws Exception {
        Mobster mobster = new Mobster("1", "john", "");
        mockMvc.perform(post("/api/v1/mobsters", mobster)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mobster)));
    }

    @Test
    public void getTotalPages() throws Exception {
        given(mobsterRepository.count()).willReturn(Long.valueOf(100));

        mockMvc.perform(get("/api/v1/mobsters/total-pages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(100)))
                .andExpect(status().isOk());
    }
}
