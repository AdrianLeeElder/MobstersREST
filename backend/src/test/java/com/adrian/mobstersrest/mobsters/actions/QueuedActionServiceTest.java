package com.adrian.mobstersrest.mobsters.actions;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.exception.BotModeNotSupportedException;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

public class QueuedActionServiceTest {

  @Mock
  private ActionExecutor actionExecutor;
  @Mock
  private MobsterService mobsterService;
  @Mock
  private AbstractAction abstractAction;

  private QueuedActionService queuedActionService;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    queuedActionService = new QueuedActionService(
        new LinkedBlockingQueue<>(Arrays.asList(abstractAction, abstractAction)),
        actionExecutor,
        mobsterService);
  }

  @Test
  public void executeNextAction() {
  }

  @Test
  public void executeQueuedActionsPass() {
    basicActionSetWithCompleteAs(true);
    given(actionExecutor.executeAction(any())).willReturn(true);
    queuedActionService.executeQueuedActions("zombie");

    assertThat(queuedActionService.getDailyActions()).isEmpty();
  }

  @Test
  public void executeQueuedActionsFailure() {
    basicActionSetWithCompleteAs(false);
    given(actionExecutor.executeAction(any())).willReturn(false);
    assertFalse(queuedActionService.executeQueuedActions("zombie"));
  }

  @Test
  public void unsupportedBotMode() {
    expectedException.expect(BotModeNotSupportedException.class);

    queuedActionService.executeQueuedActions("zombie");
  }

  private void basicActionSetWithCompleteAs(boolean complete) {
    Mobster mobster = Mobster
        .builder()
        .username("zombie")
        .build();

    given(mobsterService.setComplete(anyBoolean(), any()))
        .willReturn(Mono.just(mobster));

    queuedActionService.setBotMode(BotMode.DAILY);
  }
}