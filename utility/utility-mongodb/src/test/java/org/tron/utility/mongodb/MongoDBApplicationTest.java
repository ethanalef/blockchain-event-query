package org.tron.utility.mongodb;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tron.utility.mongodb.model.EventLog;
import org.tron.utility.mongodb.repository.EventLogRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class MongoDBApplicationTest {
  @Autowired
  EventLogRepository eventLogRepository;

  @Test
  public void test() {
    EventLog eventLog = EventLog.builder()
                          .blockNumber(BigInteger.valueOf(5000001)).blockTime(Instant.now())
                          .blockTime(Instant.now())
                          .transactionId("txn01010101")
                          .logIndex(12)
                          .contractAddress("TJMLLVAz3WaDADWdpy5u1bJbfAERdpDNYr")
                          .from("from")
                          .to("to")
                          .topics(Lists.newArrayList("t1", "t2", "t3"))
                          .dataRaw("data raw")
                          .build();
    eventLogRepository.save(eventLog);
  }
}