package com.adrian.mobsters.actions.tabaction;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.actions.extract.Bounty;
import org.springframework.stereotype.Service;

/**
 * Load the MyMobster tab page
 *
 * @author Adrian Elder <AdrianLeeElder@gmail.com>
 */
@Service
public class MyMobster extends AbstractAction {

    @Override
    public void response() {
        Bounty bounty = new Bounty();
        bounty.extract(getChromeDriver(), getMobsterUsername());
    }
}
