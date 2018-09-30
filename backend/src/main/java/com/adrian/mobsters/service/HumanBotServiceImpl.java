package com.adrian.mobsters.service;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Slf4j
@Service
public class HumanBotServiceImpl implements HumanBotService {

    private static final int MIN_SLEEP = 300;
    private static int MAX_SLEEP = 300;

    /**
     * @param min minimum number to return
     * @param max maximum number to return
     * @return Generates a number between {@code min} and {@code max}.
     */
    @Override
    public int randomNumberRange(int min, int max) {
        return new Random().nextInt((max + 1) - min) + min;
    }

    @Override
    public void randomSleep(int min, int max) {
        try {
            Thread.sleep(randomNumberRange(min, max));
        } catch (InterruptedException e) {
            log.error("Interrupted during random sleep.");
        }
    }

    /**
     * Focus on the next element in a random range between {@code 200} and {@code 300} ms.
     */
    @Override
    public void randomFocusNextElement(HtmlPage htmlPage, HtmlElement element) {
        randomSleep(MIN_SLEEP, MAX_SLEEP);
        htmlPage.setFocusedElement(element);
    }

    /**
     * Type the {@code text} inside of the {@code element} in a random manner.
     *
     * @param text    the text to type.
     * @param element the element to type in.
     */
    @Override
    public void randomTyping(String text, HtmlElement element) {
        char[] charSequence = text.toCharArray();

        for (int i = 0; i < text.length(); i++) {
            try {
                element.type(charSequence[i]);
                randomSleep(MIN_SLEEP, MAX_SLEEP);
            } catch (IOException e) {
                log.error("Interrupted while typing character sequence: " + text);
            }
        }
    }
}
