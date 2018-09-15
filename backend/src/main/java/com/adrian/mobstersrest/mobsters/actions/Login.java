package com.adrian.mobstersrest.mobsters.actions;


import com.adrian.mobstersrest.mobsters.services.HumanBotService;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebResponseData;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * @author aelder
 */
@Slf4j
public class Login extends AbstractAction {

    @Autowired
    private WebClient webClient;
    @Autowired
    private HumanBotService humanBotService;
    @Autowired
    private MobsterService mobsterService;

    private final String divisionPath = "//div[@sstyle='display:none;']";

    @Override
    public void run() {
        try {
            login();
            setPage((HtmlPage) webClient.getWebWindows().get(1).getEnclosedPage());
        } catch (IOException ex) {
            log.error("Exception", ex);
        } catch (IndexOutOfBoundsException ex) {
            log.error("Index out of bound: login page not loaded", ex);
        }
    }

    @Override
    public void response() {
    }

    private HtmlPage loginPage(String username, String password)
            throws IOException {
        final HtmlTextInput loginInputBox = (HtmlTextInput) getPage()
                .getByXPath(divisionPath + "//input[@placeholder='Type Username Here']").get(0);
        loginInputBox.getCanonicalXPath();
        final HtmlPasswordInput loginInputBoxPass = (HtmlPasswordInput) getPage()
                .getByXPath(divisionPath + "//input[@placeholder='Type Password Here']").get(0);
        loginInputBoxPass.getCanonicalXPath();
        final HtmlSubmitInput loginButton = (HtmlSubmitInput) getPage()
                .getByXPath(divisionPath
                        + "//input[@onclick=\"this.disabled=true;addValidation();this.value='Loading';form.submit()\"]")
                .get(0);
        loginButton.getCanonicalXPath();

        humanBotService.randomFocusNextElement(getPage(), loginInputBox);
        humanBotService.randomTyping(username, loginInputBox);
        humanBotService.randomSleep(50, 80);
        humanBotService.randomFocusNextElement(getPage(), loginInputBoxPass);
        humanBotService.randomTyping(password, loginInputBoxPass);
        humanBotService.randomSleep(50, 80);
        humanBotService.randomFocusNextElement(getPage(), loginButton);

        return loginButton.click();
    }

    private void login() throws IOException {
        setPage(webClient.getPage("https://app.playersrevenge.com/front.php"));
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        new FalsifyingWebConnection(webClient) {
            @Override
            public WebResponse getResponse(WebRequest request) throws IOException {
                WebResponse response = super.getResponse(request);
                if (request.getUrl().toExternalForm().contains("imm.html")) {
                    String content = "Changed the content :)";

                    WebResponseData data = new WebResponseData(content.getBytes("UTF-8"),
                            response.getStatusCode(),
                            response.getStatusMessage(), response.getResponseHeaders());
                    response = new WebResponse(data, request, response.getLoadTime());
                }
                return response;
            }
        };
        String username = getMobsterUsername();
        setPage(loginPage(username,
                mobsterService.retrieveMobsterPassword(username)));
    }


    @Override
    public void printAction() {
        log.debug(getName() + " (" + getMobsterUsername() + ")");
    }
}