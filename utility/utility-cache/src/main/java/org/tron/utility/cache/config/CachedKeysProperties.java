package org.tron.utility.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.tron.utility.common.factory.YamlPropertySourceFactory;

import java.util.Map;


@Data
@Configuration
@ConfigurationProperties(prefix = "cache")
@PropertySource(value = "classpath:cached-keys.yml", factory = YamlPropertySourceFactory.class)
public class CachedKeysProperties {
    private Map<String, Long> ttl;
}