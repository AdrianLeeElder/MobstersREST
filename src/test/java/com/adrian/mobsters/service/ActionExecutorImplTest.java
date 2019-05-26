package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.exception.ActionFailedException;
import com.gargoylesoftware.htmlunit.WebWindow;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActionExecutorImplTest {

    @Mock
    private ChromeDriver chromeDriver;
    @Mock
    private WebWindow webWindow;
    @Mock
    private HumanBotService humanBotService;
    @Mock
    private AbstractAction action;
    @Mock
    private ActionService actionService;
    private ActionExecutor actionExecutor;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        actionExecutor = new ActionExecutorImpl(humanBotService, actionService);
    }

    @Test
    public void executeAction() {
        given(action.isFinished()).willReturn(true);
        assertTrue(action.isFinished());
    }

    @Test
    public void actionFailedShouldThrowException() throws ActionFailedException {
        expectedException.expect(ActionFailedException.class);

        actionExecutor.executeAction(chromeDriver, action);
        assertTrue(action.isFinished());
    }
}