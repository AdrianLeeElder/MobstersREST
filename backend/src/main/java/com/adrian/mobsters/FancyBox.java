package com.adrian.mobsters;

import com.adrian.mobsters.actions.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FancyBox extends AbstractAction {

    public void run() {
        try {
            log.trace("Attempting to close fancy box.");
            WebElement webElement = new WebDriverWait(getChromeDriver(), 10)
                    .until(ExpectedConditions.elementToBeClickable(
                            getChromeDriver().findElement(By.xpath(
                                    getXPath()
                            ))
                    ));

            webElement.click();
        } catch (Exception ex) {
            log.info("Could not find fancy box on the page, skipping closing.", ex.getMessage());
        }
    }

    @Override
    public boolean isFinished() {
        try {
            return !getChromeDriver().findElement(By.xpath("//div[@id='fancybox-overlay']")).isDisplayed();
        } catch (Exception ex) {
            log.info("Unable to find fancy box on page, likely that it was already close, or there was not one present."
                    , ex.getMessage());
            return true;
        }
    }
}
