package com.adrian.mobsters.config;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebClientConfig {

    @Bean
    @Scope(value = "prototype")
    public WebClient webClient() {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        webClient.getOptions().setUseInsecureSSL(true);
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

        return webClient;
    }
}
