package org.tron.utility.mongodb.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.utility.mongodb.BaseTest;
import org.tron.utility.mongodb.model.FeederInfo;

import java.math.BigInteger;

public class FeederInfoServiceTest extends BaseTest {
  @Autowired
  FeederInfoService feederInfoService;

  @Test
  public void upsertTest() {
    FeederInfo feederInfo = FeederInfo.builder()
                              .event("strx-account-record")
                              .enabled(true)
                              .interval(4000)
                              .range(BigInteger.valueOf(20000))
                              .start(BigInteger.valueOf(61564523))
                              .topic(Lists.newArrayList(
                                "Deposit(address,uint256,uint256,uint256)",
                                "Withdraw(address,uint256,uint256,uint256)",
                                "Claim(address,uint256)",
                                "Transfer(address,address,uint256)"))
                              .contract(Lists.newArrayList("TU3kjFuhtEo42tsCBtfYUAZxoqQ4yuSLQ5"))
                              .build();
    feederInfoService.upsert(feederInfo);
  }
//
//  @Test
//  public void updateProcessedHeightTest() {
//    feederInfoService.updateProcessedHeight(FeederInfo.builder().event("strx-account-record").processedHeight(BigInteger.valueOf(90000000)).build());
//  }
}