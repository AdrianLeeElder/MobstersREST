package com.adrian.mobsters.service;

public enum SmsMessage {
    BOT_COMPLETE("Bot completed at {}, taking {}."),
    BOT_STARTED("Daily jobs started at {}.");

    private final String message;

    SmsMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
