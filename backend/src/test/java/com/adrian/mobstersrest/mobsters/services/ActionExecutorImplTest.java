package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;

public class ActionExecutorImplTest {

    @Mock
    private WebClient webClient;
    @Mock
    private MobsterService mobsterService;
    @Mock
    private WebWindow webWindow;
    @Mock
    private HumanBotService humanBotService;
    @Mock
    private AbstractAction action;

    private ActionExecutor actionExecutor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        actionExecutor = new ActionExecutorImpl(mobsterService, webClient, humanBotService);
    }

    @Test
    public void executeAction() {
        given(action.isFinished()).willReturn(true);
        given(webClient.getWebWindows())
                .willReturn(new ArrayList<>(Arrays.asList(webWindow, webWindow)));
        assertTrue(actionExecutor.executeAction(action));
    }

    @Test
    public void notLoggedInActionShouldFail() {
        given(webClient.getWebWindows())
                .willReturn(new ArrayList<>(Collections.singletonList(webWindow)));

        assertFalse(actionExecutor.executeAction(action));
    }
}