package com.adrian.mobsters.service;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface HumanBotService {

    int randomNumberRange(int min, int max);

    void randomSleep(int min, int max);

    void randomFocusNextElement(HtmlPage htmlPage, HtmlElement element);

    void randomTyping(String text, HtmlElement element);
}
