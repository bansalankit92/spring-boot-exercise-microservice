package com.telstra.codechallenge.helloworld;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(path = "/hello", method = RequestMethod.GET)
  public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new HelloWorld(counter.incrementAndGet(), String.format(TEMPLATE, name));
  }
}
