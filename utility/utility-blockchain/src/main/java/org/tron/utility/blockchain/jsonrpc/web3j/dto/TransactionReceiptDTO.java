package org.tron.utility.blockchain.jsonrpc.web3j.dto;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionReceiptDTO {
  private String blockNumber;
  private String contractAddress;
  private String from;
  private String to;
  private String transactionHash;
  private String transactionIndex;
  private String type;
  private List<LogDTO> logs = new ArrayList<>();

  public BigInteger getNumberDecode() {
    return Numeric.decodeQuantity(blockNumber);
  }

  public static TransactionReceiptDTO format(TransactionReceipt transactionReceipt) {
    String str = JSON.toJSONString(transactionReceipt);
    TransactionReceiptDTO transactionReceiptDTO = JSON.parseObject(str, TransactionReceiptDTO.class);
    transactionReceiptDTO.getLogs().forEach(l -> l.setLogIndex(String.valueOf(transactionReceiptDTO.getLogs().indexOf(l))));
    return transactionReceiptDTO;
  }
}