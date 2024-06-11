package org.tron.utility.cache.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCache;

/*
  L1 cache: caffeine
  L2 cache: redis
  To update the L1 cache, we’ll need to implement a custom cache interceptor to intercept whenever the cache is accessed.
 */
@RequiredArgsConstructor
public class CustomerCacheInterceptor extends CacheInterceptor {
  private final transient CacheManager caffeineCacheManager;

  @Override
  protected Cache.ValueWrapper doGet(Cache cache, Object key) {
    Cache.ValueWrapper existingCacheValue = super.doGet(cache, key);

    if (existingCacheValue != null && cache.getClass() == RedisCache.class) {
      Cache caffeineCache = caffeineCacheManager.getCache(cache.getName());
      if (caffeineCache != null) {
        caffeineCache.putIfAbsent(key, existingCacheValue.get());
      }
    }

    return existingCacheValue;
  }

  @Bean
  public CacheInterceptor cacheInterceptor(CacheManager caffeineCacheManager, CacheOperationSource cacheOperationSource) {
    CacheInterceptor interceptor = new CustomerCacheInterceptor(caffeineCacheManager);
    interceptor.setCacheOperationSources(cacheOperationSource);
    return interceptor;
  }

  @Bean
  public CacheOperationSource cacheOperationSource() {
    return new AnnotationCacheOperationSource();
  }
}
