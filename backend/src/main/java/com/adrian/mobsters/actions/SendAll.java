package com.adrian.mobsters.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendAll extends AbstractAction {

    @Autowired
    private MobCountService mobCountService;

}
