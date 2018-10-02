package com.adrian.mobsters.actions;

import java.util.List;

/**
 * A builder class to make it easier to create actions
 *
 * @author aelder
 */
public class ActionBuilder {

    private String builder_name;
    private String builder_xPath;
    private boolean builder_fancyBox;
    private List<String> builder_finishText;

    public ActionBuilder name(String name) {
        this.builder_name = name;

        return this;
    }

    public ActionBuilder fancyBox(boolean fancyBox) {
        this.builder_fancyBox = fancyBox;

        return this;
    }

    public ActionBuilder xPath(String xPath) {
        this.builder_xPath = xPath;

        return this;
    }

    public ActionBuilder finishText(List<String> finishText) {
        this.builder_finishText = finishText;

        return this;
    }

    public <A extends AbstractAction> AbstractAction build(A a) {
        a.setName(builder_name);
        a.setXPath(builder_xPath);
        a.setFinishText(builder_finishText);
        a.setFancyBox(builder_fancyBox);
        return a;
    }
}
