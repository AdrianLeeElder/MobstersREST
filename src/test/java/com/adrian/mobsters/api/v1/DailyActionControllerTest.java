package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.DailyActionWrapper;
import com.adrian.mobsters.repository.DailyActionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DailyActionControllerTest {
    private static final String BASE_API = "/api/v1/daily-actions";
    @Mock
    private DailyActionRepository dailyActionRepository;
    @InjectMocks
    private DailyActionController dailyActionController;
    private DailyAction logoutAction;
    private DailyAction loginAction;
    private DailyActionWrapper dailyActionContainer;
    private List<DailyAction> dailyActionList;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dailyActionController).build();
        loginAction = new DailyAction("LoginHtmlUnit");
        logoutAction = new DailyAction("Logout");
        dailyActionList = Arrays.asList(loginAction, logoutAction);
        given(dailyActionRepository.save(loginAction)).willReturn(loginAction);
    }

    @Test
    public void newAction() throws Exception {
        mockMvc.perform(post(BASE_API + "/add", loginAction)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(loginAction)))
        .andExpect(status().isCreated());
    }

    @Test
    public void addListOfActions() {
        dailyActionContainer = new DailyActionWrapper();
        dailyActionContainer.setDailyActions(dailyActionList);
    }
}