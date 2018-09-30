package com.adrian.mobsters.actions.tabaction;

import com.adrian.mobsters.actions.JsTabAction;
import com.adrian.mobsters.actions.extract.Bounty;
import com.adrian.mobsters.service.MobsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Load the MyMobster tab page
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Service
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
