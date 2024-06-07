package org.tron.utility.mongodb.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.utility.mongodb.BaseTest;
import org.tron.utility.mongodb.model.EventLog;

import java.math.BigInteger;
import java.time.Instant;


public class EventLogServiceTest extends BaseTest {
  @Autowired
  EventLogService eventLogService;

  @Test
  public void upsertTest() {
    EventLog eventLog1 = EventLog.builder()
                           .blockNumber(BigInteger.valueOf(69999999))
                           .blockTime(Instant.parse("2024-06-07T00:57:39Z"))
                           .transactionId("6684153383455d0a8ab0ef866edf75e785b53005003626f6ce799b578dfbce59")
                           .logIndex(0)
                           .contractAddress("TU3kjFuhtEo42tsCBtfYUAZxoqQ4yuSLQ5")
                           .from("TLKKhfWY7omsvFQfSjehA42AcJAwQrdeuR")
                           .to("TR4LLjebA9AVXY6Zt7u7EdRa8GZAgeGXn9")
                           .topics(Lists.newArrayList(
                             "ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
                             "000000000000000000000000717f2b87690d166cdc11b4bbb117969b472603f3",
                             "000000000000000000000000a58217d391a7ab21dbf4f320c2be8e95fa3b24b6"))
                           .dataRaw("00000000000000000000000000000000000000000000002086ac351052600000")
                           .build();
    EventLog eventLog2 = EventLog.builder()
                           .blockNumber(BigInteger.valueOf(69999999))
                           .blockTime(Instant.parse("2024-06-07T00:57:39Z"))
                           .transactionId("6684153383455d0a8ab0ef866edf75e785b53005003626f6ce799b578dfbce59")
                           .logIndex(0)
                           .contractAddress("TU3kjFuhtEo42tsCBtfYUAZxoqQ4yuSLQ5")
                           .from("TLKKhfWY7omsvFQfSjehA42AcJAwQrdeuR")
                           .to("TR4LLjebA9AVXY6Zt7u7EdRa8GZAgeGXn9")
                           .topics(Lists.newArrayList(
                             "ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
                             "000000000000000000000000717f2b87690d166cdc11b4bbb117969b472603f3",
                             "000000000000000000000000a58217d391a7ab21dbf4f320c2be8e95fa3b24b6"))
                           .dataRaw("00000000000000000000000000000000000000000000002086ac351052600000")
                           .build();
    eventLogService.upsert(eventLog1);
    eventLogService.upsert(Lists.newArrayList(eventLog1, eventLog2));
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