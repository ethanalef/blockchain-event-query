package org.tron.service.eventfeeder;

import org.tron.utility.blockchain.jsonrpc.web3j.dto.TransactionReceiptDTO;
import org.tron.utility.blockchain.tron.TronAddressUtils;
import org.tron.utility.blockchain.tron.TronJsonRpc;
import org.tron.utility.mongodb.model.EventLog;
import org.tron.utility.mongodb.model.FeederInfo;
import org.tron.utility.mongodb.service.EventLogService;
import org.tron.utility.mongodb.service.FeederInfoService;

import java.time.Instant;
import java.util.List;

import static org.web3j.abi.EventEncoder.buildEventSignature;

public class TronEventFeeder extends EventFeeder {
  protected TronEventFeeder(
    FeederInfo feederInfo,
    FeederInfoService feederInfoService,
    EventLogService eventLogService,
    TronJsonRpc tronJsonRpc) {
    super(feederInfo, feederInfoService, eventLogService, tronJsonRpc);
    topicHash = feederInfo.getTopic().stream().map(t -> buildEventSignature(t).substring(2)).toList();
  }

  @Override
  protected List<EventLog> getEventLog(TransactionReceiptDTO transactionReceiptDTO, Instant timestamp) {
    return transactionReceiptDTO.getLogs().stream().map(
      log -> EventLog.builder()
               .contractAddress(TronAddressUtils.hexToBase58(log.getAddress()))  // hex to base58 address
               .topics(log.getTopics().stream().map(l -> l.substring(2)).toList())  // trim 0x
               .dataRaw(log.getData().substring(2))
               .blockNumber(transactionReceiptDTO.getNumberDecode())
               .from(TronAddressUtils.hexToBase58(transactionReceiptDTO.getFrom()))
               .to(TronAddressUtils.hexToBase58(transactionReceiptDTO.getTo()))
               .transactionId(log.getTransactionHash().substring(2))
               .logIndex(Integer.parseInt(log.getLogIndex()))
               .blockTime(timestamp)
               .build()).toList();
  }
}
