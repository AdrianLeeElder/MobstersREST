package com.adrian.mobstersrest.mobsters.actions;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aelder
 */
@Slf4j
public class Logout extends AbstractAction {

  @Autowired
  private WebClient webClient;

  @Override
  public void run() {
    try {
      setPage(webClient
          .getPage("https://app.playersrevenge.com/front.php?logout=1"));
    } catch (FailingHttpStatusCodeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
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
