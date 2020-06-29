package com.tartu.library.auth.rest;

import com.tartu.library.auth.domain.model.AuthUser;
import com.tartu.library.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired UserService userService;

  @PostMapping()
  public void createUser(@RequestBody AuthUser authUser) {
    userService.save(authUser);
  }
}
