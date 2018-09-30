package com.adrian.mobsters.actions;


/**
 * @author aelder <your.name at your.org>
 */
public abstract class JsAction extends AbstractJsAction {

    @Override
    public void executeJS() {
        if (getPage() != null) {
            getPage().executeJavaScript(getScript());
        }
    }
}
