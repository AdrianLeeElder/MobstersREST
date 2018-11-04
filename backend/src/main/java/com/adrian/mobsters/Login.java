package com.adrian.mobsters;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.service.MobsterService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("login")
@Slf4j
public class Login extends AbstractAction {

    private static final String divisionPath = "//div[@sstyle='display:none;']";

    @Autowired
    private MobsterService mobsterService;

    @Override
    public void run() {
        try {
            login();
        } catch (IndexOutOfBoundsException ex) {
            log.error("Index out of bound: login page not loaded", ex);
        }
    }

    private String getLoggedInPage(String username, String password) {
        getChromeDriver()
                .findElement(By.xpath(divisionPath + "//input[@placeholder='Type Username Here']"))
                .sendKeys(username);
        getChromeDriver().findElement(By.xpath(divisionPath + "//input[@placeholder='Type Password Here']"))
                .sendKeys(password);
        getChromeDriver().findElement(By.xpath(divisionPath
                + "//input[@onclick=\"this.disabled=true;addValidation();this.value='Loading';form.submit()\"]"))
                .click();

        return getChromeDriver().getPageSource();
    }

    private void login() {
        //this will log you out
        this.getChromeDriver().get("https://app.playersrevenge.com/front.php");

        String username = getMobsterUsername();

        getLoggedInPage(username, mobsterService.retrieveMobsterPassword(username));
    }

    @Override
    public boolean isFinished() {
        if (getChromeDriver().getPageSource().contains("<iframe id=\"mprgameframe\"")) {
            switchToEmbeddedIframe();
        }

        if (super.isFinished()) {
            return true;
        }


        return false;
    }

    private void switchToEmbeddedIframe() {
        try {
            getChromeDriver().switchTo().frame("mprgameframe");
        } catch (Exception ex) {
            log.error("Unable to switch to secureframe, loading main iframe page.");
            loadIframePage();
        }
    }

    private void loadIframePage() {
        getChromeDriver().get("https://app.playersrevenge.com/iframe.php");
    }

    @Override
    public void printAction() {
        log.debug(getName() + " (" + getMobsterUsername() + ")");
    }
}
