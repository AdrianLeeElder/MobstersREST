package com.adrian.mobstersrest.mobsters.actions.tabaction;

import com.adrian.mobstersrest.mobsters.actions.JsTabAction;
import com.adrian.mobstersrest.mobsters.actions.extract.Bounty;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Load the MyMobster tab page
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
public class MyMobster extends JsTabAction {

  @Autowired
  private MobsterService mobsterService;

  @Override
  public void response() {
    Bounty bounty = new Bounty();
    bounty.extract(getPage(), getMobsterUsername());
  }

  @Override
  public String getTabScript() {
    return super.getScript(); //To change body of generated methods, choose Tools | Templates.
  }
}
