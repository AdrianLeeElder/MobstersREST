package com.adrian.mobsters.actions;

import com.adrian.mobsters.FancyBox;
import com.adrian.mobsters.Login;
import com.adrian.mobsters.exception.ActionFailedException;
import com.adrian.mobsters.service.ActionExecutor;
import com.adrian.mobsters.service.ActionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An AbstractAction is used to describe some sort of event or action that can be queued up and
 * executed by the HtmlUnit browser.
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Slf4j
@Data
public abstract class AbstractAction {

    @Autowired
    private ActionExecutor actionExecutor;
    /**
     * The name of this action.
     */
    private String name;
    /**
     * XPaths associated with this action.
     */
    private String xPath;
    /**
     * A List of possible textual options that should be the current web page to consider this action complete.
     */
    private List<String> finishText;
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
    private ChromeDriver chromeDriver;
    private String sourcePage;
    private boolean fancyBox;
    @Autowired
    private ActionService actionService;

    public String getName() {
        return name;
    }

    public void run() throws ActionFailedException {
        log.trace("Running action {}", getName());
        WebElement webElement = getChromeDriver().findElement(By.xpath(getXPath()));

        if (webElement.isDisplayed()) {
            webElement.click();
        }
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    public void response() throws ActionFailedException {
        if (fancyBox) {
            closeFancyBox();
        }
    }

    private void closeFancyBox() throws ActionFailedException {
        log.trace("Attempting to close fancy box.");
        FancyBox fancyBox = (FancyBox) actionService.getAction("Fancy Box");
        actionExecutor.executeAction(getChromeDriver(), fancyBox);
    }

    /**
     * @return Whether or not this action is finished executing
     */
    public boolean isFinished() {
        log.trace("Polling finished status for {}", getName());
        switchToEmbeddedIframe();
        String content = getChromeDriver().getPageSource();
        if (isFinished) {
            setFinished(true);
            return true;
        }

        for (String text : finishText) {
            if (content != null) {
                Pattern pattern = Pattern.compile(text);
                Matcher matcher = pattern.matcher(content);

                if (matcher.find()) {
                    setFinished(true);

                    return true;
                }
            }
        }

        return false;
    }

    public void switchToEmbeddedIframe() {
        if (this instanceof Login) {
            List<WebElement> webElements = getChromeDriver().findElements(By.xpath("//*[@id=\"mprgameframe\"]"));

            if (webElements.size() > 0) {
                getChromeDriver().switchTo().frame(webElements.get(0));
            }
        }
    }

    /**
     * Describe how this action will be displayed (usually in a log)
     */
    public void printAction() {
        //log.info("");
    }
}
