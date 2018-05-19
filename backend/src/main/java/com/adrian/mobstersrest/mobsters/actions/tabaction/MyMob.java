package com.adrian.mobstersrest.mobsters.actions.tabaction;

import com.adrian.mobstersrest.mobsters.actions.AbstractJsAction;
import com.adrian.mobstersrest.mobsters.actions.JsTabAction;
import com.adrian.mobstersrest.mobsters.services.ActionService;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MyMob extends JsTabAction {

  private ActionService actionService;

  @Override
  public void response() {
    HtmlDivision div = (HtmlDivision) getPage().getFirstByXPath("//div[@id='tmob']/div/div");
    Pattern pattern = Pattern
        .compile("(Hired Guns: )([0-9]+)( \\+ Active Top Mob Slots: )([0-9]+)");
    Matcher m = pattern.matcher(div.asText());

    if (m.find()) {
      int myMobCount = Integer.parseInt(m.group(4));

      ((AbstractJsAction) actionService.getAction("Send All"))
          .setScript("sendalle(" + myMobCount + ");");
      ((AbstractJsAction) actionService.getAction("Collect All"))
          .setScript("collectallc(" + myMobCount + ");");
    }
  }
}
