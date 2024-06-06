package org.tron.utility.blockchain.jsonrpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.tron.utility.common.factory.YamlPropertySourceFactory;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "jrpc-nodes")
@PropertySource(value = "classpath:${spring.profiles.active}/jrpc-nodes.yml", factory = YamlPropertySourceFactory.class)
public class JsonRpcNodes {
  private double rate;
  private List<Map<String, String>> tron;
  private List<Map<String, String>> eth;
}