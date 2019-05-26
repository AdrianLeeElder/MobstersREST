package com.adrian.mobsters.exception;

import com.adrian.mobsters.actions.BotMode;

public class BotModeNotSupportedException extends RuntimeException {

    public BotModeNotSupportedException(BotMode botMode) {
        super("Bot Mode (" + botMode + ") is not supported.");
    }
}
