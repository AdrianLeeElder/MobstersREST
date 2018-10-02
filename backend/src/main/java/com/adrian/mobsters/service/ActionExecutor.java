package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.exception.ActionFailedException;
import org.openqa.selenium.chrome.ChromeDriver;

public interface ActionExecutor {

    /**
     * Execute an {@link AbstractAction} using the given web client.
     *
     * @param chromeDriver  the shared web client.
     * @param currentAction the action to execute.
     */
    void executeAction(ChromeDriver chromeDriver, AbstractAction currentAction) throws ActionFailedException;
}
