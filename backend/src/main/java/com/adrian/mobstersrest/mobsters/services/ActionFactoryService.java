package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;

public interface ActionFactoryService {

  AbstractAction getAbstractAction(String actionName);
}
