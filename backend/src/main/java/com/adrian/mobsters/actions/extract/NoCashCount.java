package com.adrian.mobsters.actions.extract;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class NoCashCount implements ExtractAction {

    @Override
    public void extract(ChromeDriver chromeDriver, String username) {
        //TODO: FIX PROPERTY BUYER
        WebElement div = chromeDriver.findElement(By.xpath("fancybox-content"));

        //PropertyBuyer.setCouldNotBuySomeLast(div.asXml().contains("You don't have enough"));
    }
}
