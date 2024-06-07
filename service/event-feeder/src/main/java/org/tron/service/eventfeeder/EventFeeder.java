package org.tron.service.eventfeeder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tron.utility.blockchain.jsonrpc.web3j.Web3jQuery;
import org.tron.utility.blockchain.jsonrpc.web3j.dto.TransactionReceiptDTO;
import org.tron.utility.mongodb.model.EventLog;
import org.tron.utility.mongodb.model.FeederInfo;
import org.tron.utility.mongodb.service.EventLogService;
import org.tron.utility.mongodb.service.FeederInfoService;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public abstract class EventFeeder {
  @Getter
  private final FeederInfo feederInfo;
  private final FeederInfoService feederInfoService;
  private final EventLogService eventLogService;
  protected final Web3jQuery web3jQuery;
  protected BigInteger processedHeight;
  protected List<String> topicHash;

  protected BigInteger getProcessedHeight() {
    if (processedHeight != null) {
      return processedHeight;
    }
    FeederInfo info = feederInfoService.getFeederInfoByEvent(feederInfo.getEvent());
    return Optional.ofNullable(info).map(FeederInfo::getProcessedHeight).orElse(feederInfo.getStart());
  }

  protected void updateProcessedHeight(BigInteger height) {
    processedHeight = height;
    FeederInfo info = FeederInfo.builder().event(feederInfo.getEvent()).processedHeight(processedHeight).build();
    feederInfoService.updateProcessedHeight(info);
  }

  public void step() {
    if (feederInfo.isEnabled()) {
      BigInteger from = getProcessedHeight().add(BigInteger.ONE);
      try {
        step(from);
      } catch (Exception e) {
        log.error("Exception occurs at event feeder " + feederInfo.getEvent(), e);
      }
    }
  }

  public void step(BigInteger from) {
    // TODO: use @Timed for measuring cost
    long t = System.currentTimeMillis();
    BigInteger currentHeight = web3jQuery.getCurrentHeight();
    if (currentHeight.compareTo(from) < 0) {
      log.warn("Query height = {} but current height = {} diff = {}}", currentHeight, from, from.subtract(currentHeight));
      return;
    }
    BigInteger to = currentHeight.compareTo(from.add(feederInfo.getRange())) < 0 ? currentHeight : from.add(feederInfo.getRange());
    List<EventLog> eventLogs = fetchEventLog(from, to);
    int found = eventLogs.size();
    if (found == 1) {
      // Use single upsert for only one entry
      eventLogService.upsert(eventLogs.getFirst());
      log.info("{} Found 1", feederInfo.getEvent());
    } else if (found > 1) {
      // Use bulk upsert for more than one entry
      eventLogService.upsert(eventLogs);
      log.info("{} Found {}", feederInfo.getEvent(), eventLogs.size());
    }
    updateProcessedHeight(to);
    log.debug("{} step is finished in {} ms", feederInfo.getEvent(), System.currentTimeMillis() - t);
  }

  public List<EventLog> fetchEventLog(BigInteger from, BigInteger to) {
    log.info("{} Fetch from {} to {}", feederInfo.getEvent(), from, to);

    // get height-txnHash
    EthFilter filter = web3jQuery.getEthFilter(from, to, feederInfo.getTopic(), feederInfo.getContract());
    SortedMap<BigInteger, LinkedHashSet<String>> blockTxnMap = web3jQuery.getBlockTxnMap(filter);
    if (blockTxnMap.isEmpty()) {
      return Collections.emptyList();
    }

    List<EventLog> result = new ArrayList<>();
    for (var entry : blockTxnMap.entrySet()) {
      // get block info with timestamp
      Instant timestamp = web3jQuery.getBlockTime(entry.getKey());

      // get transaction logs
      List<TransactionReceiptDTO> transactionLogDTOList = entry.getValue().stream()
                                                            .map(web3jQuery::getTransactionReceiptDTO)
                                                            .filter(Objects::nonNull)
                                                            .toList();

      // get event logs
      result.addAll(transactionLogDTOList.stream().flatMap(
        transactionReceiptDTO -> getEventLog(transactionReceiptDTO,  timestamp).stream()).toList());
    }

    return result.stream().filter(this::isTarget).toList();
  }

  private boolean isTarget(EventLog eventLog) {
    boolean matchTopic = topicHash.isEmpty() || topicHash.contains(eventLog.getTopics().getFirst());
    boolean matchAddr = feederInfo.getContract().isEmpty() || feederInfo.getContract().contains(eventLog.getContractAddress());
    return matchTopic && matchAddr;
  }

  protected abstract List<EventLog> getEventLog(TransactionReceiptDTO transactionReceiptDTO, Instant timestamp);
}
