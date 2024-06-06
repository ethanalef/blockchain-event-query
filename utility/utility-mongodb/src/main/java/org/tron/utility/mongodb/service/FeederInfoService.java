package org.tron.utility.mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tron.utility.mongodb.model.FeederInfo;
import org.tron.utility.mongodb.repository.FeederInfoRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeederInfoService {

  private final FeederInfoRepository feederInfoRepository;

  public void upsert(FeederInfo feederInfo) {
    FeederInfo existingFeederInfo = feederInfoRepository.findByEvent(feederInfo.getEvent());
    if (existingFeederInfo != null) {
      // Update the existing record
      feederInfo.setId(existingFeederInfo.getId());
    }
    feederInfoRepository.save(feederInfo);
  }

  public void updateProcessedHeight(FeederInfo feederInfo) {
    FeederInfo existingFeederInfo = feederInfoRepository.findByEvent(feederInfo.getEvent());
    if (existingFeederInfo != null) {
      // Update the existing record
      existingFeederInfo.setProcessedHeight(feederInfo.getProcessedHeight());
      feederInfoRepository.save(existingFeederInfo);
    }
  }

  public FeederInfo findByEvent(String event) {
    return feederInfoRepository.findByEvent(event);
  }

  public List<FeederInfo> findAll() {
    return feederInfoRepository.findAll();
  }
}