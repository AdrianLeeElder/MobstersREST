package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.exception.ActionFailedException;
import com.gargoylesoftware.htmlunit.WebClient;

public interface ActionExecutor {

    /**
     * Execute an {@link AbstractAction} using the given web client.
     * @param webClient the shared web client.
     * @param currentAction the action to execute.
     */
    void executeAction(WebClient webClient, AbstractAction currentAction) throws ActionFailedException;
}
