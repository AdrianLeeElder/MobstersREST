package com.adrian.mobsters.actions;

import com.adrian.mobsters.exception.ActionFailedException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An AbstractAction is used to describe some sort of event or action that can be queued up and
 * executed by the HtmlUnit browser.
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Slf4j
@Data
public abstract class AbstractAction {

    /**
     * The name of this action.
     */
    private String name;
    /**
     * The DOM element ID associated with this action.
     */
    private String elementID;
    /**
     * XPaths associated with this action.
     */
    private String xPath;
    /**
     * A List of possible textual options that should be the current web page to consider this action complete.
     */
    private List<String> finishText;
    /**
     * The current displayed page.
     */
    private HtmlPage page;
    /**
     * Whether or not this action is complete.
     */
    private boolean isFinished;
    /**
     * Whether or not this action failed.
     */
    private boolean failed;
    /**
     * Whether or not this action is still running.
     */
    private boolean running;
    /**
     * The message to display one this action completes.
     */
    private String completeMessage = "";
    /**
     * A mobster username that is executing the action.
     */
    private String mobsterUsername;
    /**
     * A {@link} WebClient that persist across multiple actions. This is set from the
     * {@link com.adrian.mobsters.service.ActionExecutor}.
     */
    private WebClient webClient;
//    /**
//     * Store properties in a shared action context, so that actions can share
//     */
//    private Map<String, String> actionContext;

    public String getName() {
        return name;
    }

    public abstract void run() throws ActionFailedException;

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
        Objects.requireNonNull(page);
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
