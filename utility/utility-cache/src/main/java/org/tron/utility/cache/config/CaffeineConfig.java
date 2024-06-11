package org.tron.utility.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CaffeineConfig {
  private final CachedKeysProperties cachedKeysProperties;

  @Bean
  @Primary
  @Qualifier("L1CacheManager")
  public CacheManager caffeineCacheManager() {
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    simpleCacheManager.setCaches(getCacheConfig());
    return simpleCacheManager;
  }

  private List<CaffeineCache> getCacheConfig() {
    List<CaffeineCache> config = new ArrayList<>();
    Map<String, Long> map = cachedKeysProperties.getTtl();
    map.forEach((key, value) -> config.add(
      new CaffeineCache(key, Caffeine.newBuilder()
                               .expireAfterWrite(Duration.ofMinutes(1))
                               .initialCapacity(1)
                               .maximumSize(2000)
                               .build())
    ));
    return config;
  }
}
