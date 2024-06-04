package org.tron.utility.mongodb.model;

import lombok.Builder;
import lombok.experimental.Accessors;
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
public record EventLog(
  @MongoId(FieldType.OBJECT_ID)
  String id,
  @Indexed
  BigInteger blockNumber,
  Instant blockTime,
  String transactionId,
  int logIndex,
  String contractAddress,
  String from,
  String to,
  List<String> topics,
  String dataRaw
) {}