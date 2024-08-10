package com.dduckddak.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("/health-check")
  public String healthCheck() {
    return "I'm OK";
  }
}