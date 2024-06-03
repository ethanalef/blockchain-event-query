package org.tron.eventfeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "org.tron.utility")
public class EventFeederApplication {
  public static void main(String[] args) {
    SpringApplication.run(EventFeederApplication.class, args);
  }
}
