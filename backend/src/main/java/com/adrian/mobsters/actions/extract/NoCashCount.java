package com.adrian.mobsters.actions.extract;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

@Service
public class NoCashCount implements ExtractAction {

    @Override
    public void extract(HtmlPage page, String username) {
        //TODO: FIX PROPERTY BUYER
        HtmlDivision div = (HtmlDivision) page.getElementById("fancybox-content");
        //PropertyBuyer.setCouldNotBuySomeLast(div.asXml().contains("You don't have enough"));
    }
}
