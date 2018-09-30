package com.adrian.mobsters.actions;

import com.adrian.mobsters.exception.MobCountNotSetException;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.NonNull;
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
     * @param htmlPage the page that contains the mob count.
     * @return the mob count.
     */
    public int getMobCount(@NonNull HtmlPage htmlPage) {
        HtmlDivision div = htmlPage.getFirstByXPath("//div[@id='tmob']/div/div");
        Matcher m = getDivMatcher(div);

        return getMobCountFromDivMatcher(m);
    }

    private Matcher getDivMatcher(@NonNull HtmlDivision div) {
        Pattern pattern = Pattern.compile("(Hired Guns: )([0-9]+)( \\+ Active Top Mob Slots: )([0-9]+)");

        return pattern.matcher(div.asText());
    }

    private Integer getMobCountFromDivMatcher(@NonNull Matcher m) {
        if (m.find()) {
            return Integer.parseInt(m.group(4));
        }

        throw new MobCountNotSetException();
    }
}
