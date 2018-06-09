package com.adrian.mobstersrest.mobsters.services;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.adrian.mobstersrest.mobsters.actions.BotMode;
import com.adrian.mobstersrest.mobsters.actions.Login;
import com.adrian.mobstersrest.mobsters.actions.Logout;
import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.exception.BotModeNotSupportedException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class QueuedActionServiceImplTest {

  @Mock
  private ActionExecutor actionExecutor;
  @Mock
  private MobsterService mobsterService;
  @Mock
  private AbstractAction abstractAction;

  @InjectMocks
  private QueuedActionServiceImpl queuedActionServiceImpl;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() throws NoSuchFieldException, IllegalAccessException {
    List<AbstractAction> dailyActions = new ArrayList<>();
    dailyActions.add(new Login());
    dailyActions.add(new Logout());

    Field actions = queuedActionServiceImpl.getClass().getDeclaredField("dailyActions");
    actions.setAccessible(true);
    actions.set(queuedActionServiceImpl, new LinkedBlockingQueue<>(dailyActions));
  }

  @Test
  public void executeNextAction() {
  }

  @Test
  public void executeQueuedActionsPass() {
    basicActionSetWithCompleteAs(true);
    given(actionExecutor.executeAction(any())).willReturn(true);
    queuedActionServiceImpl.executeQueuedActions("zombie");

    assertThat(queuedActionServiceImpl.getDailyActions()).isEmpty();
  }

  @Test
  public void executeQueuedActionsFailure() {
    basicActionSetWithCompleteAs(false);
    given(actionExecutor.executeAction(any())).willReturn(false);
    assertFalse(queuedActionServiceImpl.executeQueuedActions("zombie"));
  }

  @Test
  public void unsupportedBotMode() {
    expectedException.expect(BotModeNotSupportedException.class);

    queuedActionServiceImpl.executeQueuedActions("zombie");
  }

  private void basicActionSetWithCompleteAs(boolean complete) {
    Mobster mobster = Mobster
        .builder()
        .username("zombie")
        .build();

    given(mobsterService.setComplete(anyBoolean(), any()))
        .willReturn(Mono.just(mobster));

    queuedActionServiceImpl.setBotMode(BotMode.DAILY);
  }
}