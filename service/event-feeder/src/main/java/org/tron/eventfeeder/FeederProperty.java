package org.tron.eventfeeder;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

import static org.web3j.abi.EventEncoder.buildEventSignature;

@Data
public class FeederProperty {
  private String event;
  private boolean enabled;
  private long interval;
  private BigInteger range;
  private BigInteger start;
  private List<String> topic;
  private List<String> contract;

  public List<String> getTopicHash() {
    return topic.stream().map(t -> buildEventSignature(t).substring(2)).toList();
  }
}
