package com.adrian.mobsters.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectAll extends JsAction {

    @Autowired
    private MobCountService mobCountService;

    @Override
    public void executeJS() {
        setScript("collectallc(" + mobCountService.getMobCount(getPage()) + ");");
        super.executeJS();
    }
}
