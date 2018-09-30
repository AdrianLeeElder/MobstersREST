package com.adrian.mobsters.actions;


import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ActionFailedException;
import com.adrian.mobsters.exception.ProxyCouldNotConnectException;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import com.adrian.mobsters.service.HumanBotService;
import com.adrian.mobsters.service.MobsterService;
import com.adrian.mobsters.service.proxy.ProxyService;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipException;


/**
 * @author aelder
 */
@Slf4j
@Service
public class Login extends AbstractAction {

    @Autowired
    private HumanBotService humanBotService;
    @Autowired
    private MobsterService mobsterService;
    @Autowired
    private ProxyReactiveRepository proxyReactiveRepository;
    @Autowired
    private ProxyService proxyService;
    private final String divisionPath = "//div[@sstyle='display:none;']";
    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 10;

    @Override
    public void run() throws ActionFailedException {
        try {
            loginAttempts++;
            login();
            setPage((HtmlPage) getWebClient().getWebWindows().get(1).getEnclosedPage());
        } catch (IOException ex) {
            log.error("Exception", ex);
        } catch (IndexOutOfBoundsException ex) {
            log.error("Index out of bound: login page not loaded", ex);
        } catch (ProxyCouldNotConnectException e) {
            if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
                Proxy proxy = proxyReactiveRepository.findByHost(
                        getWebClient()
                                .getOptions()
                                .getProxyConfig()
                                .getProxyHost())
                        .block();

                proxyService.handleProxyFailure(proxy);
                Proxy newProxy = proxyService.getAvailableProxy();
                getWebClient().getOptions().setProxyConfig(new ProxyConfig(newProxy.getHost(), newProxy.getPort()));
                loginAttempts++;
            } else {
                throw new ActionFailedException("login");
            }
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

        HtmlPage result = null;
        try {
            result = loginButton.click();
        } catch (ZipException ex) {
            log.info("Login page not in GZIP format. This is expected.");

            Objects.requireNonNull(result);
        }
        return result;
    }

    private void login() throws IOException, ProxyCouldNotConnectException {
        try {
            setPage((HtmlPage) getWebClient().getPage("https://app.playersrevenge.com/front.php"));
        } catch (HttpHostConnectException ex) {
            throw new ProxyCouldNotConnectException(getWebClient().getOptions().getProxyConfig().toString());
        }
        getWebClient().getOptions().setThrowExceptionOnScriptError(false);

        String username = getMobsterUsername();
        setPage(loginPage(username,
                mobsterService.retrieveMobsterPassword(username)));
    }


    @Override
    public void printAction() {
        log.debug(getName() + " (" + getMobsterUsername() + ")");
    }
}