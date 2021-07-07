package com.tartu.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LibraryApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
  }
}
