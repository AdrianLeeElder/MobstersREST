package com.adrian.mobsters.exception;

import com.adrian.mobsters.actions.AbstractAction;

public class ActionFailedException extends Throwable {

    public ActionFailedException(AbstractAction abstractAction) {
        super(abstractAction.toString());
    }

    public ActionFailedException(String action) {
        super(action);
    }
}
