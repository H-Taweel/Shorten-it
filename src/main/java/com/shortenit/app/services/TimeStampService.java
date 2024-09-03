package com.shortenit.app.services;

import com.shortenit.app.model.TimeStamping;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeStampService {
  public TimeStamping createTimeStamp() {
    return new TimeStamping(Instant.now());
  }
}
