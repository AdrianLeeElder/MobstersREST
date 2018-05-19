package com.adrian.mobstersrest.mobsters.exception;

/**
 * @author aelder
 */
public class NotLoggedInException extends RuntimeException {

  public NotLoggedInException(String username) {
    super(username + " not logged in.");
  }

}
