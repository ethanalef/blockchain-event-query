package org.tron.service.eventfeeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.tron.utility.blockchain.tron.TronJsonRpc;

import java.math.BigInteger;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHelper {
  private final TronJsonRpc tronJsonRpc;

  @Cacheable(cacheNames = "current-height", cacheManager = "L1CacheManager", sync=true)
  public BigInteger getTronCurrentHeight() {
    log.debug("Cache missed: getTronCurrentHeight");
    return tronJsonRpc.getCurrentHeight();
  }

  @Cacheable(cacheNames = "blocktime", cacheManager = "L1CacheManager", sync=true)
  public Instant getTronBlockTime(BigInteger height) {
    log.debug("Cache missed: getBlockTime {}", height);
    return tronJsonRpc.getBlockTime(height);
  }
}

