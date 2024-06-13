package org.tron.utility.cache;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Worker {
  private final CacheHelper cacheHelper;

  public void work() {
    System.out.println("working...");
    cacheHelper.getCachedValue();
  }
}
