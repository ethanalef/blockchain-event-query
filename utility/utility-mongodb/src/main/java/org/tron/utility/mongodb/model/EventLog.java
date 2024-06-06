package org.tron.utility.mongodb.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * Composite key { transactionId, logIndex } is unique
 * @param id
 * @param blockNumber
 * @param blockTime
 * @param transactionId
 * @param logIndex
 * @param contractAddress
 * @param from
 * @param to
 * @param topics
 * @param dataRaw
 */
@Document(collection = "eventlog")
@Accessors(chain = true)
@Builder
@Data
@CompoundIndex(name = "transactionId_logIndex_idx", def = "{'transactionId': 1, 'logIndex': 1}", unique = true)
public class EventLog {
  @MongoId(FieldType.OBJECT_ID)
  private String id;
  @Indexed
  private BigInteger blockNumber;
  private Instant blockTime;
  @Indexed
  String transactionId;
  private int logIndex;
  private String contractAddress;
  private String from;
  private String to;
  private List<String> topics;
  private String dataRaw;
}