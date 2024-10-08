package org.tron.utility.blockchain.tron;

import org.junit.jupiter.api.Test;
import org.tron.utility.blockchain.BaseTest;

class ContractsUtilsTest extends BaseTest {

  @Test
  void base58ToHexTest() {
    String base58Address = TronAddressUtils.base58ToHex("TZ8du1HkatTWDbS6FLZei4dQfjfpSm9mxp");
  }

  @Test
  void encode58CheckTest() {
    String base58Address = TronAddressUtils.hexToBase58("f0927876c8a1a8129d0e7ce3bdd4fd252ddfe9f3");
  }

  @Test
  void test() {
    String from = TronAddressUtils.hexToBase58("0xda76eb7174c659c0bbfa413e46c2f83cb7b41aa7");
    String to = TronAddressUtils.hexToBase58("0x26a733f2df06bb49dd6b7723cd033245108a4b47");
    String address = TronAddressUtils.hexToBase58("0xeca9bc828a3005b9a3b909f2cc5c2a54794de05f");
  }
}
