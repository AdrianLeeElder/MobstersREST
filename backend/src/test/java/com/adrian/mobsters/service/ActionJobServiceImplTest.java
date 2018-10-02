package com.adrian.mobsters.service;

import com.adrian.mobsters.Login;
import com.adrian.mobsters.config.ActionExecutorProperties;
import com.adrian.mobsters.domain.Action;
import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ActionFailedException;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import com.adrian.mobsters.service.proxy.ProxyService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobServiceImplTest {

    @Mock
    private ActionExecutor actionExecutor;
    @Mock
    private ActionExecutorProperties actionExecutorProperties;
    @Mock
    private ActionService actionService;
    @Mock
    private ActionJobReactiveRepository actionJobReactiveRepository;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private ProxyService proxyService;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClientOptions webClientOptions;
    @Mock
    private ProxyReactiveRepository proxyReactiveRepository;
    @InjectMocks
    private ActionJobServiceImpl actionJobServiceImpl;
    private ActionJob actionJob;
    private List<Action> actionList;
    private Proxy proxy;
    private Login loginHtmlUnit;

    @Before
    public void setUp() throws Exception, ActionFailedException {
        proxy = new Proxy("localhost", 2323);
        Mobster mobster = new Mobster("1", "BOB", "");
        actionList = Collections.singletonList(new Action("LoginHtmlUnit"));
        actionJob = new ActionJob(mobster, actionList, true, false);
        loginHtmlUnit = new Login();

        given(webClient.getOptions()).willReturn(webClientOptions);
        given(proxyService.getAvailableProxy()).willReturn(proxy);
        given(applicationContext.getBean("webClient")).willReturn(webClient);
        given(actionService.getAction(anyString())).willReturn(loginHtmlUnit);
        given(actionJobReactiveRepository.save(actionJob)).willReturn(Mono.just(actionJob));
        given(proxyReactiveRepository.save(proxy)).willReturn(Mono.just(proxy));
        given(actionExecutorProperties.getMaxFailures()).willReturn(1);
    }

    @Test
    public void runActionJob() {
        actionJobServiceImpl.run(actionJob);

        for (Action action : actionList) {
            assertTrue(action.isComplete());
            assertFalse(action.isRunning());
        }

        assertEquals(proxy.getAttempts(), 1);
        assertEquals(proxy.getSuccesses(), 1);
        assertTrue(actionJob.isComplete());

    }

    @Test
    public void actionJobFailure() throws ActionFailedException {
        doThrow(ActionFailedException.class).when(actionExecutor).executeAction(any(), any());
        actionJobServiceImpl.run(actionJob);

        assertTrue(actionJob.isFrozen());
    }
}