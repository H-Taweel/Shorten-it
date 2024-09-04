package com.shortenit.app.controller;

import com.shortenit.app.model.EndUserRegistration;
import com.shortenit.app.persistence.EndUser;
import com.shortenit.app.services.EndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/end_users")
public class EndUserController {
  @Autowired EndUserService endUserService;

  public EndUserController(EndUserService endUserService) {
    this.endUserService = endUserService;
  }

  @PostMapping("/create-user")
  public EndUser post(@RequestBody final EndUserRegistration registration) {
    return endUserService.create(registration);
  }

  @GetMapping("/users/{id}")
  public Optional<EndUser> get(@PathVariable final UUID id) {
    return endUserService.get(id);
  }
}
