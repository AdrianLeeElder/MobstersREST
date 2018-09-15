package com.adrian.mobstersrest.mobsters.actions;

import com.adrian.mobstersrest.mobsters.actions.extract.BuyNext;
import com.adrian.mobstersrest.mobsters.actions.extract.NoCashCount;
import com.adrian.mobstersrest.mobsters.services.HumanBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BuyProperty extends JsAction {

    @Autowired
    private HumanBotService humanBotService;

    @Override
    public void response() {
        BuyNext buyNext = new BuyNext();
        buyNext.extract(getPage(), getMobsterUsername());

        NoCashCount noCashCount = new NoCashCount();
        noCashCount.extract(getPage(), getMobsterUsername());

        //PropertyBuyer.buyProperty(getPage(), getMobster());
        humanBotService.randomSleep(2000, 4000);
    }
}
