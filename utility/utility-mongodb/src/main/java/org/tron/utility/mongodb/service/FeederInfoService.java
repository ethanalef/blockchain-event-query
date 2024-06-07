package org.tron.utility.mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tron.utility.mongodb.model.FeederInfo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeederInfoService {

  private final MongoTemplate mongoTemplate;

  public FeederInfo getFeederInfoByEvent(String event) {
    return Optional.ofNullable(mongoTemplate.findOne(createEventQuery(event), FeederInfo.class))
             .orElseThrow(() -> new IllegalArgumentException("Feeder not found: " + event));
  }

  public FeederInfo upsert(FeederInfo feederInfo) {
    Query query = createEventQuery(feederInfo.getEvent());
    FeederInfo existingFeederInfo = mongoTemplate.findOne(query, FeederInfo.class);
    if (existingFeederInfo != null) {
      Update update = createUpdateFromFeederInfo(feederInfo);
      return mongoTemplate.findAndModify(query, update, FeederInfo.class);
    } else {
      return mongoTemplate.save(feederInfo);
    }
  }

  public void updateProcessedHeight(FeederInfo feederInfo) {
    Query query = createEventQuery(feederInfo.getEvent());
    Update update = new Update().set("processedHeight", feederInfo.getProcessedHeight());
    mongoTemplate.findAndModify(query, update, FeederInfo.class);
  }

  public List<FeederInfo> findAll() {
    return mongoTemplate.findAll(FeederInfo.class);
  }

  private Query createEventQuery(String event) {
    return new Query(Criteria.where("event").is(event));
  }

  private Update createUpdateFromFeederInfo(FeederInfo feederInfo) {
    return new Update()
             .set("enabled", feederInfo.isEnabled())
             .set("interval", feederInfo.getInterval())
             .set("range", feederInfo.getRange())
             .set("start", feederInfo.getStart())
             .set("topic", feederInfo.getTopic())
             .set("contract", feederInfo.getContract())
             .set("processedHeight", feederInfo.getProcessedHeight());
  }
}