package com.shortenit.app.controller;

import com.shortenit.app.model.CreateGreetingRequest;
import com.shortenit.app.model.Greeting;
import com.shortenit.app.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  @Autowired GreetingService service;

  @PostMapping("/greetings")
  public Greeting greet(@RequestBody final CreateGreetingRequest request) {
    return service.create(request);
  }
}
