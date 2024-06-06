package org.tron.utility.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tron.utility.mongodb.model.FeederInfo;

@Repository
public interface FeederInfoRepository extends MongoRepository<FeederInfo, String> {
  FeederInfo findByEvent(String event);
}
