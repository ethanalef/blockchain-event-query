package org.tron.service.eventfeeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tron.utility.blockchain.tron.TronJsonRpc;
import org.tron.utility.mongodb.service.EventLogService;
import org.tron.utility.mongodb.service.FeederInfoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeederManager {
  private final FeederInfoService feederInfoService;
  private final EventLogService eventLogService;
  private final TronJsonRpc tronJsonRpc;
  private final CacheHelper cacheHelper;
  private Set<EventFeeder> eventFeeders;
  private ScheduledExecutorService scheduler;

  @PostConstruct
  public void init() {
    eventFeeders = new HashSet<>();
    feederInfoService.findAll()
      .forEach(feederInfo -> {
        eventFeeders.add(new TronEventFeeder(feederInfo, feederInfoService, eventLogService, tronJsonRpc, cacheHelper));
        log.info("Register event-feeder {}", feederInfo.getEvent());
      });
    scheduler = Executors.newScheduledThreadPool(eventFeeders.size());
  }

  public void startSchedulers() {
    for (EventFeeder feeder : eventFeeders) {
      long interval = feeder.getFeederInfo().getInterval();
      scheduler.scheduleAtFixedRate(feeder::step, 0, interval, TimeUnit.MILLISECONDS);
    }
  }

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {
    startSchedulers();
  }
}