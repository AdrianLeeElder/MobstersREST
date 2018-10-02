package com.adrian.mobsters.config;

import com.adrian.mobsters.service.proxy.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class WebDriverConfig {

    @Autowired
    private ProxyService proxyService;

    @Bean
    @Scope(value = "prototype")
    public ChromeOptions chromeOptions() {
        Proxy proxy = new Proxy();

        com.adrian.mobsters.domain.Proxy availableProxy = proxyService.getAvailableProxy();
        proxy.setSslProxy(availableProxy.getHost() + ":" + availableProxy.getPort());

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        desiredCapabilities.setCapability("proxy", proxy);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors",
                "--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36");

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        log.info("Starting new chrome driver with new proxy: {}", availableProxy);
        return chromeOptions;
    }
}
