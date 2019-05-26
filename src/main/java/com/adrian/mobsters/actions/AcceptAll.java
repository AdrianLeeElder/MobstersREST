package com.adrian.mobsters.actions;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * @author aelder
 */
@Service
public class AcceptAll extends AbstractAction {

    private int friendCount = 0;

    @Override
    public void run() {
        friendCount = getFriendCount();

        if (friendCount > 0) {
            //TODO: accept friends
        } else {
            setFinished(true);
        }
    }

    @Override
    public void response() {

    }

    @Override
    public void printAction() {

    }

    //dynamically pull friend count from page
    private int getFriendCount() {
        WebElement element = getChromeDriver().findElementById("pendingMob");
        return Integer.parseInt(element.getText().replace("(", "").replace(")", ""));
    }
}
