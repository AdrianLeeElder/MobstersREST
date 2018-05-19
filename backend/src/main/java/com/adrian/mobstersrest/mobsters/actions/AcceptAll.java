package com.adrian.mobstersrest.mobsters.actions;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/**
 * @author aelder
 */
public class AcceptAll extends JsAction {

  private int friendCount = 0;

  @Override
  public void run() {
    friendCount = getFriendCount();

    if (friendCount > 0) {
      super.executeJS();
    } else {
      setFinished(true);
    }
  }

  @Override
  public void response() {

  }

  //dynamically pull friend count from page
  private int getFriendCount() {
    HtmlDivision element = (HtmlDivision) getPage().getElementById("pendingMob");
    return Integer.parseInt(element.getTextContent().replace("(", "").replace(")", ""));
  }
}
