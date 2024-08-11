package com.dduckddak.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Component
@Slf4j
public class EndpointLister implements CommandLineRunner {

  @Autowired
  private RequestMappingHandlerMapping handlerMapping;

  @Override
  public void run(String... args) throws Exception {
    Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
    map.forEach((key, value) -> log.info(key + " " + value));
  }
}