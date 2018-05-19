package com.adrian.mobstersrest.mobsters.actions;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * An AbstractAction is used to describe some sort of event or action that can be queued up and
 * executed by the HtmlUnit browser.
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractAction {

  private String name;
  private String elementID;
  private String xPath;
  private List<String> finishText;
  private HtmlPage page;
  private boolean isFinished;
  private boolean failed;
  private boolean running;
  private String completeMessage = "";
  private String mobsterUsername;

  public String getName() {
    return name;
  }

  public abstract void run();

  public String getElementID() {
    return elementID;
  }

  public String getXPath() {
    return xPath;
  }

  public abstract void response();

  /**
   * An HtmlPage instance used for extractions or executing an AbstractAction.
   *
   * @return the page attached to this action
   */
  public HtmlPage getPage() {
    return page;
  }

  /**
   * Set the page attached to this action
   *
   * @param page the page to attach to this action
   */
  public void setPage(HtmlPage page) {
    this.page = page;
  }

  /**
   * @return Whether or not this action is finished executing
   */
  public boolean isFinished() {
    String content = null;
    if (page != null) {
      content = page.asXml();
      //TODO: figure out what to do with this;
    }

    if (isFinished) {
      setFinished(true);
      return true;
    }

    for (String text : finishText) {
      if (content != null && content.matches("(?s).*?" + text + ".*")) {
        setFinished(true);
        return true;
      }
    }

    return false;
  }

  /**
   * A list of possible matches on the current page that would indicate this action has successfully
   * executed.
   */
  public List<String> getFinishedText() {
    return new ArrayList<>(finishText);
  }

  /**
   * Describe how this action will be displayed (usually in a log)
   */
  public abstract void printAction();
}