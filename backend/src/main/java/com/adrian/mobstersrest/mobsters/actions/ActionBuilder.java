package com.adrian.mobstersrest.mobsters.actions;

import java.util.List;

/**
 * A builder class to make it easier to create actions
 *
 * @author aelder
 */
public class ActionBuilder {

  private String builder_name;
  private String builder_xPath;
  private String builder_elementID;
  private List<String> builder_finishText;
  private String builder_script;
  private int builder_tab;

  public ActionBuilder name(String name) {
    this.builder_name = name;

    return this;
  }

  public ActionBuilder xPath(String xPath) {
    this.builder_xPath = xPath;

    return this;
  }

  public ActionBuilder elementID(String elementID) {
    this.builder_elementID = elementID;

    return this;
  }

  public ActionBuilder tab(int tab) {
    this.builder_tab = tab;

    return this;
  }

  public ActionBuilder finishText(List<String> finishText) {
    this.builder_finishText = finishText;

    return this;
  }

  public ActionBuilder script(String script) {
    this.builder_script = script;

    return this;
  }

  public <A extends AbstractAction> AbstractAction build(A a) {
    a.setName(builder_name);
    a.setElementID(builder_elementID);
    a.setXPath(builder_xPath);
    a.setFinishText(builder_finishText);

    ActionBuilderFactory factory = new ActionBuilderFactory();
    factory.build(a);
    return a;
  }

  /**
   * Handles anomalous attributes that deviate from AbstractAction
   */
  private class ActionBuilderFactory {

    private <A extends AbstractAction> AbstractAction build(A a) {
      if (a instanceof AbstractJsAction) {
        ((AbstractJsAction) a).setScript(ActionBuilder.this.builder_script);
      }

      if (a instanceof JsTabAction) {
        ((JsTabAction) a).setTabID(builder_tab);
      }

      return a;
    }
  }
}
