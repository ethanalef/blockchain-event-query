package org.tron.utility.mongodb.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "feederinfo")
@Accessors(chain = true)
@Builder
@Data
public class FeederInfo {
  @MongoId(FieldType.OBJECT_ID)
  private String id;
  @Indexed(unique = true)
  private String event;
  private boolean enabled;
  private long interval;
  private BigInteger range;
  private BigInteger start;
  private List<String> topic;
  private List<String> contract;
  private BigInteger processedHeight;
}