package com.adrian.mobsters.actions;

import com.adrian.mobsters.exception.MobCountNotSetException;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A service to extract mob count from an html page.
 */
@Service
public class MobCountService {

    /**
     * Extract the mob count from the given page.
     *
     * @param chromeDriver the page that contains the mob count.
     * @return the mob count.
     */
    public int getMobCount(@NonNull ChromeDriver chromeDriver) {
        WebElement div = chromeDriver.findElement(By.xpath("//div[@id='tmob']/div/div"));
        Matcher m = getDivMatcher(div);

        return getMobCountFromDivMatcher(m);
    }

    private Matcher getDivMatcher(@NonNull WebElement div) {
        Pattern pattern = Pattern.compile("(Hired Guns: )([0-9]+)( \\+ Active Top Mob Slots: )([0-9]+)");

        return pattern.matcher(div.getText());
    }

    private Integer getMobCountFromDivMatcher(@NonNull Matcher m) {
        if (m.find()) {
            return Integer.parseInt(m.group(4));
        }

        throw new MobCountNotSetException();
    }
}
