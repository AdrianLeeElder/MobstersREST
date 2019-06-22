package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class ActionJobCreatorImplTest {
    private static final String TRACY = "tracy";
    private static final List<Action> ACTION_LIST = Arrays.asList(Action
                    .builder()
                    .name("Login")
                    .sequence(0)
                    .build(),
            Action
                    .builder()
                    .name("200 Energy Link")
                    .sequence(1)
                    .build(),
            Action
                    .builder()
                    .name("Logout")
                    .sequence(2)
                    .build());
    private static final List<ActionTemplateAction> ACTION_LIST_TEMPLATE = Collections
            .singletonList(ActionTemplateAction.builder().name("200 Energy Link").build());
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
                .actions(ACTION_LIST_TEMPLATE)
                .name("Daily Actions")
                .mobsters(Collections.singletonList(mobster))
                .build();

        assertEquals(Collections
                        .singletonList(ActionJob
                                .builder()
                                .createdDate(NOW)
                                .priority(1)
                                .template(template)
                                .actionList(ACTION_LIST)
                                .user(TRACY).mobster(mobster)
                                .build()),
                actionJobCreatorImpl.create(template, "tracy"));
    }
}