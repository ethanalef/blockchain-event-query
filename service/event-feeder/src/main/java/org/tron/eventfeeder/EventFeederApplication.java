package org.tron.eventfeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tron.utility.blockchain.tron.TronJsonRpc;

@SpringBootApplication(scanBasePackages = "org.tron.utility")
public class EventFeederApplication {

  private final TronJsonRpc tronJsonRpc;

  public EventFeederApplication(TronJsonRpc tronJsonRpc) {
    this.tronJsonRpc = tronJsonRpc;
  }

  public static void main(String[] args) {
    SpringApplication.run(EventFeederApplication.class, args);
  }

}
