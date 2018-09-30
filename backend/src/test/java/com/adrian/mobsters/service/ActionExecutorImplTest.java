package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.exception.ActionFailedException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActionExecutorImplTest {

    @Mock
    private WebClient webClient;
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
        given(webClient.getWebWindows())
                .willReturn(new ArrayList<>(Arrays.asList(webWindow, webWindow)));
        assertTrue(action.isFinished());
    }

    @Test
    public void actionFailedShouldThrowException() throws ActionFailedException {
        expectedException.expect(ActionFailedException.class);
        given(webClient.getWebWindows())
                .willReturn(new ArrayList<>(Collections.singletonList(webWindow)));

        given(action.getWebClient()).willReturn(webClient);
        actionExecutor.executeAction(webClient, action);
        assertTrue(action.isFinished());
    }
}