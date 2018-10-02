package com.adrian.mobsters.actions.extract;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class BankCash implements ExtractAction {

    @Override
    public void extract(ChromeDriver chromeDriver, String username) {
        //TODO: implement XPath version.
//    HtmlElement element = (HtmlElement) page.getElementById("myCashLogg");
//    elementText = element.asText();
//    extractedText = elementText.replace("Currently in your bank account", "").trim();
//    String filteredBankNumber = extractedText.replace(": ", "");

        //TODO: Add MobsterServer methods for setting these.
//    mobster.setBankCash(new BigInteger(filteredBankNumber));
//    mobster.setBankStartCash(new BigInteger(filteredBankNumber));
    }
}
