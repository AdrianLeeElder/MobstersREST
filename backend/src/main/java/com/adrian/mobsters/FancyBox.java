package com.adrian.mobsters;

import com.adrian.mobsters.actions.AbstractAction;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
public class FancyBox extends AbstractAction {

    @Override
    public boolean isFinished() {
        return !getChromeDriver().findElement(By.xpath("//div[@id='fancybox-overlay']")).isDisplayed();
    }
}
