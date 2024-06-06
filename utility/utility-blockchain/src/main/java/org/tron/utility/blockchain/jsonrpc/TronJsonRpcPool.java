package org.tron.utility.blockchain.jsonrpc;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tron.utility.blockchain.jsonrpc.web3j.Web3jWrapper;

import java.util.List;

@Slf4j
@Configuration
public class TronJsonRpcPool implements JsonRpcPool {
  private final JsonRpcNodes jsonRpcNodes;
  private final RateLimiter rateLimiter;

  public TronJsonRpcPool(JsonRpcNodes jsonRpcNodes) {
    this.jsonRpcNodes = jsonRpcNodes;
    this.rateLimiter = RateLimiter.create(jsonRpcNodes.getRate());
  }

  @Bean(name = "tronWeb3jPool")
  public List<Web3jWrapper> web3jPool() {
    return createWeb3jPool(jsonRpcNodes.getTron());
  }

  @Override
  public Web3jWrapper getWeb3j() {
    long t1 = System.currentTimeMillis();
    rateLimiter.acquire();
    log.debug("acquire {} {}ms", web3jPool().get(index.get() % web3jPool().size()).getIdentifier(), System.currentTimeMillis() - t1);
    int currentIndex = index.getAndIncrement() % web3jPool().size();
    return web3jPool().get(currentIndex);
  }
}
