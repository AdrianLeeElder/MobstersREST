package com.adrian.mobstersrest.mobsters.actions;

import lombok.extern.slf4j.Slf4j;

/**
 * A JavaScript action that can be executed for use with a browser that supports it.
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Slf4j
public abstract class AbstractJsAction extends AbstractAction {

    private String script;

    /**
     * @return The JavaScript code for this AbstractJsAction
     */
    public String getScript() {
        return script;
    }

    /**
     * Change the JavaScript code that is executed.
     *
     * @param script the new JavaScript code to use
     */
    public void setScript(final String script) {
        this.script = script;
    }

    /**
     * Executed by a queued action manager
     */
    @Override
    public void run() {
    }

    @Override
    public void printAction() {
        log.info(getName(), getMobsterUsername());
    }

    @Override
    public void response() {
    }

    protected abstract void executeJS();
}
