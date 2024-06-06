package org.tron.utility.mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tron.utility.mongodb.model.EventLog;
import org.tron.utility.mongodb.repository.EventLogRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventLogService {
  private final EventLogRepository eventLogRepository;

  public void upsert(EventLog eventLog) {
    // Check if an EventLog with the same transactionId and logIndex already exists
    EventLog existingEventLog = eventLogRepository.findByTransactionIdAndLogIndex(eventLog.getTransactionId(), eventLog.getLogIndex());
    if (existingEventLog != null) {
      // Update the existing EventLog
      existingEventLog.setBlockNumber(eventLog.getBlockNumber());
      existingEventLog.setBlockTime(eventLog.getBlockTime());
      existingEventLog.setContractAddress(eventLog.getContractAddress());
      existingEventLog.setFrom(eventLog.getFrom());
      existingEventLog.setTo(eventLog.getTo());
      existingEventLog.setTopics(eventLog.getTopics());
      existingEventLog.setDataRaw(eventLog.getDataRaw());
      eventLogRepository.save(existingEventLog);
    } else {
      // Insert the new EventLog
      eventLogRepository.save(eventLog);
    }
  }

  public void upsert(List<EventLog> eventLogs) {
    List<EventLog> logsToUpdate = new ArrayList<>();
    List<EventLog> logsToInsert = new ArrayList<>();

    for (EventLog eventLog : eventLogs) {
      // Check if an EventLog with the same transactionId and logIndex already exists
      EventLog existingEventLog = eventLogRepository.findByTransactionIdAndLogIndex(eventLog.getTransactionId(), eventLog.getLogIndex());
      if (existingEventLog != null) {
        // Update the existing EventLog
        existingEventLog.setBlockNumber(eventLog.getBlockNumber());
        existingEventLog.setBlockTime(eventLog.getBlockTime());
        existingEventLog.setContractAddress(eventLog.getContractAddress());
        existingEventLog.setFrom(eventLog.getFrom());
        existingEventLog.setTo(eventLog.getTo());
        existingEventLog.setTopics(eventLog.getTopics());
        existingEventLog.setDataRaw(eventLog.getDataRaw());
        logsToUpdate.add(existingEventLog);
      } else {
        // Insert the new EventLog
        logsToInsert.add(eventLog);
      }
    }

    // Perform batch save for updates and inserts
    if (!logsToUpdate.isEmpty()) {
      eventLogRepository.saveAll(logsToUpdate);
    }
    if (!logsToInsert.isEmpty()) {
      eventLogRepository.saveAll(logsToInsert);
    }
  }
}