package org.tron.service.eventfeeder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.utility.blockchain.tron.TronJsonRpc;

import java.math.BigInteger;
import java.time.Instant;

public class ModuleTests extends BaseTest {
  @Autowired
  TronJsonRpc tronJsonRpc;
  @Autowired
  CacheHelper cacheHelper;

  @Test
  public void test() {
    boolean isContract = tronJsonRpc.isContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    Assertions.assertTrue(isContract);
  }

  @Test
  public void cacheHelperTest() {
    BigInteger v = cacheHelper.getTronCurrentHeight();
    Assertions.assertNotNull(v);

    BigInteger b = BigInteger.valueOf(62490000L);
    Instant w = cacheHelper.getTronBlockTime(b);
    Assertions.assertNotNull(w);
  }
}
