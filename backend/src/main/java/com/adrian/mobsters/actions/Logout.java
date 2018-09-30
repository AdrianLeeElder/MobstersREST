package com.adrian.mobsters.actions;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author aelder
 */
@Slf4j
@Service
public class Logout extends AbstractAction {

    @Override
    public void run() {
        try {
            setPage(getWebClient()
                    .getPage("https://app.playersrevenge.com/front.php?logout=1"));
        } catch (FailingHttpStatusCodeException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void response() {
//    if (getMobster().failedOnAction()) {
//      Client.closeClient();
//    }
    }

    @Override
    public void printAction() {
        log.debug("Logout", getMobsterUsername());
    }
}
