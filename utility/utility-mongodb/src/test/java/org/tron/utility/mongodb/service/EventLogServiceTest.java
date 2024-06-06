package org.tron.utility.mongodb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.utility.mongodb.BaseTest;


public class EventLogServiceTest extends BaseTest {
  @Autowired
  EventLogService eventLogService;

  @Test
  public void upsertTest() {
//    EventLog eventLog1 = EventLog.builder()
//                          .blockNumber(BigInteger.valueOf(5000001)).blockTime(Instant.now())
//                          .blockTime(Instant.now())
//                          .transactionId("txn01010101")
//                          .logIndex(12)
//                          .contractAddress("TJMLLVAz3WaDADWdpy5u1bJbfAERdpDNYr")
//                          .from("from")
//                          .to("to")
//                          .topics(Lists.newArrayList("t1", "t2", "t3"))
//                          .dataRaw("data raw")
//                          .build();
//    eventLogService.upsert(eventLog1);
//
//    EventLog eventLog2 = EventLog.builder()
//                          .blockNumber(BigInteger.valueOf(5000002)).blockTime(Instant.now())
//                          .blockTime(Instant.now())
//                          .transactionId("txn01010102")
//                          .logIndex(12)
//                          .contractAddress("TJMLLVAz3WaDADWdpy5u1bJbfAERdpDNYr")
//                          .from("from")
//                          .to("to")
//                          .topics(Lists.newArrayList("t1", "t2", "t3"))
//                          .dataRaw("data raw")
//                          .build();
//    eventLogService.upsert(eventLog2);
//
//    EventLog eventLog3 = EventLog.builder()
//                           .blockNumber(BigInteger.valueOf(5000003)).blockTime(Instant.now())
//                           .blockTime(Instant.now())
//                           .transactionId("txn01010103")
//                           .logIndex(12)
//                           .contractAddress("TJMLLVAz3WaDADWdpy5u1bJbfAERdpDNYr")
//                           .from("from")
//                           .to("to")
//                           .topics(Lists.newArrayList("t1", "t2", "t3"))
//                           .dataRaw("data raw")
//                           .build();
//    eventLogService.upsert(eventLog3);
//
//    eventLogService.upsert(Lists.newArrayList(eventLog1, eventLog2));
  }
}