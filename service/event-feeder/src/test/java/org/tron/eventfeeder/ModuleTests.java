package org.tron.eventfeeder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.utility.blockchain.tron.TronJsonRpc;

public class ModuleTests extends BaseTest {
  @Autowired
  TronJsonRpc tronJsonRpc;

  @Test
  public void test() {
    boolean isContract = tronJsonRpc.isContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    Assertions.assertTrue(isContract);
  }
}
