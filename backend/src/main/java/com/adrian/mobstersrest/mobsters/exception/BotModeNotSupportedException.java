package com.adrian.mobstersrest.mobsters.exception;

import com.adrian.mobstersrest.mobsters.actions.BotMode;

public class BotModeNotSupportedException extends RuntimeException {

  public BotModeNotSupportedException(BotMode botMode) {
    super("Bot Mode (" + botMode + ") is not supported.");
  }
}
