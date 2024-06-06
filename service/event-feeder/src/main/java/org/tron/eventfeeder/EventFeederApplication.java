package org.tron.eventfeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"org.tron.eventfeeder", "org.tron.utility"})
@EnableScheduling
public class EventFeederApplication {
  public static void main(String[] args) {
    SpringApplication.run(EventFeederApplication.class, args);
  }
}
