package org.tron.utility.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tron.utility.mongodb.model.EventLog;

@Repository
public interface EventLogRepository extends MongoRepository<EventLog, String> {
}
