package org.tron.utility.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CacheHelper {
  private AtomicInteger atomicInteger = new AtomicInteger(0);

  @Cacheable(cacheNames = "cached-key-in-300-seconds", cacheManager = "L1CacheManager")
//  @Caching(cacheable = {
//    @Cacheable(cacheNames = "cached-key-in-300-seconds", cacheManager = "L1CacheManager"),
//    @Cacheable(cacheNames = "cached-key-in-300-seconds", cacheManager = "L2CacheManager")
//  })
  public BigInteger getCachedValue() {
    System.out.println("cache miss");
    return BigInteger.valueOf(atomicInteger.getAndIncrement());
  }

  public Integer getUncachedValue() {
    return atomicInteger.getAndIncrement();
  }
}
