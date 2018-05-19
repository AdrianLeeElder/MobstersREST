package com.adrian.mobstersrest.mobsters.webclient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return new WebClient(BrowserVersion.CHROME);
  }
}
