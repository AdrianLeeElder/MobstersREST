package com.adrian.mobsters;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.service.MobsterService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
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

    @Override
    public void response() {
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
        this.getChromeDriver().get("https://app.playersrevenge.com/front.php");

        String username = getMobsterUsername();

        setSourcePage(
                getLoggedInPage(username,
                        mobsterService.retrieveMobsterPassword(username))
        );
    }


    @Override
    public void printAction() {
        log.debug(getName() + " (" + getMobsterUsername() + ")");
    }
}
