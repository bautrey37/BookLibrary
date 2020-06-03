package com.tartu.library.common.application.exception;

import org.springframework.validation.BindingResult;

import java.util.List;

public class ValidationErrorException extends Exception {
  private final List<BindingResult> bindingResults;

  public ValidationErrorException(List<BindingResult> bindingResults) {
    this.bindingResults = bindingResults;
  }

  public List<BindingResult> getBindingResults() {
    return this.bindingResults;
  }
}
