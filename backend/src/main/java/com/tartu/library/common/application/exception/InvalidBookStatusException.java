package com.tartu.library.common.application.exception;

public class InvalidBookStatusException extends Exception {
  private static final long serialVersionUID = 1L;

  public InvalidBookStatusException(String message) {
    super(String.format("Book Status is invalid. %s", message));
  }
}
