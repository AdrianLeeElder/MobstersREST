package com.adrian.mobsters.actions.extract;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

@Service
public class Bounty implements ExtractAction {

    @Override
    public void extract(HtmlPage page, String username) {
        //TODO:  Create MobsterService setBounty
//    HtmlSpan bountySpan = (HtmlSpan) page.getElementById("bounty");
//    elementText = bountySpan.asText();
//    extractedText = elementText.replaceAll("[$//,]*", "");
//
//    if (!extractedText.isEmpty() || extractedText.equals("0")) {
//      mobster.setBountyProperty(new CashBigInteger(extractedText));
//    }
    }
}
