package com.shortenit.app.controller;

import com.shortenit.app.model.TimeStamping;
import com.shortenit.app.services.TimeStampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeStampController {
  @Autowired TimeStampService timeStampService;

  @GetMapping("/time")
  public TimeStamping timeStamping() {
    return timeStampService.createTimeStamp();
  }
}
