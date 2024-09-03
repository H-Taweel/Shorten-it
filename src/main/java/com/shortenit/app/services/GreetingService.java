package com.shortenit.app.services;

import com.shortenit.app.model.CreateGreetingRequest;
import com.shortenit.app.model.Greeting;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GreetingService {
  public Greeting create(final CreateGreetingRequest request) {
    return new Greeting(UUID.randomUUID(), request.name());
  }
}
