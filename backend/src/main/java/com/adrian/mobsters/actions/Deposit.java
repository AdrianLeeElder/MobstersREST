package com.adrian.mobsters.actions;

import com.adrian.mobsters.actions.extract.BankCash;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Deposit extends JsAction {

    @Override
    public void run() {
        HtmlButton button2 = (HtmlButton) getPage()
                .getByXPath("//div[@id='bank']//button[@class='refreshButtonDiv']")
                .get(0);

        if (button2 == null) {
            log.debug("Error getting Bank tab button");
            throw new IllegalStateException("Error getting bank tab button.");
        }

        this.setScript(button2.getAttribute("onclick")
                .replace("setTimeout(function(){_ext2=$('#popup').html();GetPage(3)},350);", ""));
        super.run(); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void response() {
        BankCash bankCashExtractAction = new BankCash();

        bankCashExtractAction.extract(getPage(), getMobsterUsername());
    }
}
