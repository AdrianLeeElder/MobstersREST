package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.DailyActionContainer;
import com.adrian.mobsters.repository.DailyActionReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DailyActionControllerTest {

    private static final String BASE_API = "api/v1/dailyaction";
    @Mock
    private DailyActionReactiveRepository dailyActionReactiveRepository;
    private WebTestClient webTestClient;
    private DailyAction logoutAction;
    private DailyAction loginAction;
    private DailyActionContainer dailyActionContainer;
    private List<DailyAction> dailyActionList;

    @Before
    public void setUp() {
        webTestClient = WebTestClient
                .bindToController(new DailyActionController(dailyActionReactiveRepository))
                .build();

        loginAction = new DailyAction("LoginHtmlUnit");
        logoutAction = new DailyAction("Logout");
        dailyActionList = Arrays.asList(loginAction, logoutAction);
        given(dailyActionReactiveRepository.save(loginAction)).willReturn(Mono.just(loginAction));
    }

    @Test
    public void newAction() {
        webTestClient
                .post()
                .uri(BASE_API + "/add")
                .body(Mono.just(loginAction), DailyAction.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(DailyAction.class);
    }

    @Test
    public void addListOfActions() {
        dailyActionContainer = new DailyActionContainer();
        dailyActionContainer.setDailyActions(
                dailyActionList
        );

        given(dailyActionReactiveRepository.saveAll(dailyActionList))
                .willReturn(Flux.fromIterable(dailyActionList));

        webTestClient
                .post()
                .uri(BASE_API + "/addlist")
                .body(Mono.just(dailyActionContainer), DailyActionContainer.class)
                .exchange()
                .expectStatus().isAccepted();
    }
}