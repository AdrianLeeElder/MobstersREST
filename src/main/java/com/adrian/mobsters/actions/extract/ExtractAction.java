package com.adrian.mobsters.actions.extract;


import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Adrian
 */
public interface ExtractAction {

    void extract(ChromeDriver chromeDriver, String username);
}
