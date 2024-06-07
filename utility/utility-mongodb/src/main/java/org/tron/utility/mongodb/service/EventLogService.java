package org.tron.utility.mongodb.service;

import lombok.RequiredArgsConstructor;
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

@Service
@Transactional
@RequiredArgsConstructor
public class EventLogService {
  private final MongoTemplate mongoTemplate;

  public void upsert(EventLog eventLog) {
    Query query = createQuery(eventLog);
    Update update = createUpdate(eventLog);

    mongoTemplate.findAndModify(query, update,
      new FindAndModifyOptions().returnNew(true).upsert(true),
      EventLog.class);
  }

  public void upsert(List<EventLog> eventLogs) {
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