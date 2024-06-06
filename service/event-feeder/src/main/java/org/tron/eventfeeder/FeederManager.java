package org.tron.eventfeeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tron.utility.blockchain.tron.TronJsonRpc;
import org.tron.utility.mongodb.service.EventLogService;
import org.tron.utility.mongodb.service.FeederInfoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FeederManager {
  private final FeederInfoService feederInfoService;
  private final EventLogService eventLogService;
  private final TronJsonRpc tronJsonRpc;
  private Set<EventFeeder> eventFeeders;
  private ScheduledExecutorService scheduler;

  @PostConstruct
  public void init() {
    eventFeeders = new HashSet<>();
    feederInfoService.findAll()
      .forEach(feederInfo -> eventFeeders.add(new TronEventFeeder(feederInfo, feederInfoService, eventLogService, tronJsonRpc)));
    scheduler = Executors.newScheduledThreadPool(eventFeeders.size());
    startSchedulers();
  }

  public void startSchedulers() {
    for (EventFeeder feeder : eventFeeders) {
      long interval = feeder.getFeederInfo().getInterval();
      scheduler.scheduleAtFixedRate(feeder::step, 0, interval, TimeUnit.MILLISECONDS);
    }
  }

  //  @Scheduled(fixedDelay = 5000L)
//  public void runTesk() {
//    try {
//      eventFeeders.forEach(EventFeeder::step);
//    } catch (Exception e) {
//      int n = 0;
//    }
//  }
}