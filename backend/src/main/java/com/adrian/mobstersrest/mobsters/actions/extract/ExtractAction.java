package com.adrian.mobstersrest.mobsters.actions.extract;


import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Adrian
 */
public interface ExtractAction {

  void extract(HtmlPage htmlPage, String username);
}
