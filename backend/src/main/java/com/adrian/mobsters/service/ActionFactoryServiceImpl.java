package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Adrian
 */
@Slf4j
@Service
public class ActionFactoryServiceImpl implements ActionFactoryService {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Create a new AbstractAction object with the passed in namename.
     *
     * @param actionName the name of the AbstractAction
     * @return a new AbstractAction instance.
     */
    @Override
    public AbstractAction getAbstractAction(@NonNull String actionName) {
        return (AbstractAction) applicationContext.getBean(getFormatedActionName(actionName));
    }

    private String getFormatedActionName(String actionName) {
        actionName = actionName.replaceAll("[0-9 ]", "");
        String firstChar = String.valueOf(actionName.charAt(0)).toLowerCase();
        actionName = actionName.substring(1, actionName.length());
        return firstChar + actionName;
    }
}
