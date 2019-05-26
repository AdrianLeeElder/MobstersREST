package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;

public interface ActionFactoryService {

    AbstractAction getAbstractAction(String actionName);
}
