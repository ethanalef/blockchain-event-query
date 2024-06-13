package org.tron.utility.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CacheApplication.class)
@ActiveProfiles("prod")
public class CacheTest {

  @Autowired
  CacheHelper cacheHelper;
  @Autowired
  Manager manager;

  @Test
  public void cacheHelperTest() {
    BigInteger v = cacheHelper.getCachedValue();
    v = cacheHelper.getCachedValue();
    Assertions.assertEquals(0, v);

    int w = cacheHelper.getUncachedValue();
    w = cacheHelper.getUncachedValue();
    Assertions.assertEquals(1, w);
  }

  @Test
  public void threadedTest() throws InterruptedException {
    System.out.println("Start manager");
    manager.startSchedulers();
    Thread.sleep(60000L);
  }
}