package com.adrian.mobsters.actions;

/**
 * @author aelder
 */
public abstract class JsTabAction extends AbstractJsAction {

    private int tabID;

    protected int getTabID() {
        return tabID;
    }

    protected void setTabID(int tabID) {
        this.tabID = tabID;
    }

    @Override
    protected void executeJS() {
        if (getPage() != null) {
            getPage().executeJavaScript(getTabScript());
        }
    }

    public String getTabScript() {
        return String.format("GetPage(%s);", tabID);
    }
}
