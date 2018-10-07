package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.service.ActionJobCreator;
import com.adrian.mobsters.service.ActionJobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobControllerTest {

    @Mock
    private ActionJobReactiveRepository actionJobReactiveRepository;
    @Mock
    private ActionJobService actionJobService;
    @Mock
    private ActionJobCreator actionJobCreator;
    @InjectMocks
    private ActionJobController actionJobController;
    private WebTestClient webTestClient;
    private static final String API_BASE = "api/v1/actionjob";
    private Mobster mobster;
    private ActionJob actionJob;
    private List<DailyAction> actionList;
    private DailyAction loginAction;

    @Before
    public void setup() {
        webTestClient = WebTestClient
                .bindToController(
                        actionJobController)
                .configureClient()
                .build();

        mobster = new Mobster("1", "zombie", "");
        loginAction = new DailyAction("LoginHtmlUnit");

        actionJob = new ActionJob(mobster,
                Collections.singletonList(new Action(loginAction.getName())), true, false);
        actionJob.setId("1");

        actionList = Collections.singletonList(
                loginAction
        );
    }

    @Test
    public void createActionJobForMobster() {
        given(actionJobCreator.getNewDailyActionJobs("zombie")).willReturn(Flux.just(actionJob));
        webTestClient
                .get()
                .uri(API_BASE + "/new/zombie")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(ActionJob.class)
                .consumeWith(response -> {
                    List<ActionJob> actionJobList = response.getResponseBody();

                    assertNotNull(actionJobList);
                    assertEquals(Collections.singletonList(this.actionJob), actionJobList);
                });
    }

    @Test
    public void viewJob() {
        given(actionJobReactiveRepository.findById("1")).willReturn(Mono.just(actionJob));
        webTestClient
                .get()
                .uri(API_BASE + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActionJob.class)
                .consumeWith(response -> {
                    ActionJob actionJob = response.getResponseBody();

                    assertNotNull(actionJob);
                    assertEquals(this.actionJob, actionJob);
                });
    }

    @Test
    public void queueAll() {
        given(actionJobCreator.getNewDailyJobForAllMobsters()).willReturn(Flux.just(actionJob));

        webTestClient
                .get()
                .uri(API_BASE + "/queueall")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(ActionJob.class)
                .consumeWith(response -> {
                    List<ActionJob> actionJobList = response.getResponseBody();

                    assertNotNull(actionJobList);
                    assertEquals(Collections.singletonList(this.actionJob), actionJobList);
                });

    }
}
