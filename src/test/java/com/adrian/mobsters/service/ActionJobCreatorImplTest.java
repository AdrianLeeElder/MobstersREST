package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobCreatorImplTest {
    private static final String TRACY = "tracy";
    private static final List<Action> ACTION_LIST = Collections.singletonList(Action.builder().name("Login").build());
    private static final LocalDateTime NOW = LocalDateTime.now();
    private ActionJobCreatorImpl actionJobCreatorImpl;

    @Before
    public void setUp() {
        actionJobCreatorImpl = spy(new ActionJobCreatorImpl());
        given(actionJobCreatorImpl.getDateTime()).willReturn(NOW);
    }

    @Test
    public void createFromTemplate() {
        Mobster mobster = Mobster
                .builder()
                .user(TRACY)
                .build();

        ActionTemplate template = ActionTemplate
                .builder()
                .user(TRACY)
                .actionsList(ACTION_LIST)
                .name("Daily Actions")
                .build();

        List<Mobster> mobsters = Collections.singletonList(mobster);

        assertEquals(Collections
                        .singletonList(ActionJob.builder().createdDate(NOW).priority(1).actionList(ACTION_LIST)
                                .user(TRACY).mobster(mobster).build()),
                actionJobCreatorImpl.createFromTemplate(template, Collections.singletonList(mobster)));
    }
}