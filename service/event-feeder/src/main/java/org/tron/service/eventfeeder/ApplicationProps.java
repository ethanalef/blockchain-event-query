package org.tron.service.eventfeeder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationProps {
  private List<FeederProperty> feeders;
}
