package com.adrian.mobsters.actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author aelder
 */
@Slf4j
@Service
public class Logout extends AbstractAction {

    @Override
    public void run() {
        getChromeDriver().get("https://app.playersrevenge.com/front.php?logout=1");
    }

    @Override
    public void response() {
    }

    @Override
    public void printAction() {
        log.debug("Logout", getMobsterUsername());
    }
}
