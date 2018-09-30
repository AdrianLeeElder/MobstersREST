package com.adrian.mobsters.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class SSLProxyDownloader implements ProxyDownloader {

    private static final String SSL_PROXIES_BASE_URL = "https://www.us-proxy.org/";
    private final HttpClient httpClient;
    private final BasicResponseHandler basicResponseHandler;

    @Override
    public String getProxyPage() {
        HttpGet httpGet = new HttpGet(SSL_PROXIES_BASE_URL);

        try {
            return basicResponseHandler.handleResponse(httpClient.execute(httpGet));
        } catch (IOException e) {
            log.error("Unable to retrieve SSL proxy page.", e);
            return "";
        }
    }
}
