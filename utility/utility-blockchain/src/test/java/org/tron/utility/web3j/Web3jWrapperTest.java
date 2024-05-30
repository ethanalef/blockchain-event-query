package org.tron.utility.web3j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tron.utility.BaseTest;
import org.tron.utility.jsonrpc.JsonRpcPool;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

class Web3jWrapperTest extends BaseTest {
  @Qualifier("tronJsonRpcPool")
  @Autowired
  JsonRpcPool jsonRpcPool;

  @Test
  void ethGetBlockByNumberTest() throws IOException {
    Web3jWrapper web3jWrapper = jsonRpcPool.getWeb3j();
    EthBlock block = web3jWrapper.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(46633933)));
    List<EthBlock.TransactionResult> txn = block.getBlock().getTransactions();
  }
}
