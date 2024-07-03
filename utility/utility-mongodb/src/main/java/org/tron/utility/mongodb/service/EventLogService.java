package org.tron.utility.mongodb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tron.utility.mongodb.model.EventLog;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventLogService {
  private final MongoTemplate mongoTemplate;

  public void upsert(EventLog eventLog) {
    long t = System.currentTimeMillis();
    Query query = createQuery(eventLog);
    Update update = createUpdate(eventLog);

    mongoTemplate.findAndModify(query, update,
      new FindAndModifyOptions().returnNew(true).upsert(true),
      EventLog.class);
    log.debug("upsert cost {} ms", System.currentTimeMillis() - t);
  }

  public void upsert(List<EventLog> eventLogs) {
    long t = System.currentTimeMillis();
    if (eventLogs == null || eventLogs.isEmpty()) {
      throw new IllegalArgumentException("The list of event logs is empty.");
    }

    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, EventLog.class);

    for (EventLog eventLog : eventLogs) {
      Query query = createQuery(eventLog);
      Update update = createUpdate(eventLog);
      bulkOps.upsert(query, update);
    }

    bulkOps.execute();
    log.debug("bulk upsert {} cost {} ms", eventLogs.size(), System.currentTimeMillis() - t);
  }

  private Query createQuery(EventLog eventLog) {
    return new Query().addCriteria(Criteria.where("transactionId").is(eventLog.getTransactionId())
                                     .and("logIndex").is(eventLog.getLogIndex()));
  }

  private Update createUpdate(EventLog eventLog) {
    return new Update()
             .set("blockNumber", eventLog.getBlockNumber())
             .set("blockTime", eventLog.getBlockTime())
             .set("contractAddress", eventLog.getContractAddress())
             .set("from", eventLog.getFrom())
             .set("to", eventLog.getTo())
             .set("topics", eventLog.getTopics())
             .set("dataRaw", eventLog.getDataRaw());
  }
}