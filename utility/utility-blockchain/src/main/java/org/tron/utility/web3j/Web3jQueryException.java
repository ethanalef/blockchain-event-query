package org.tron.utility.web3j;

public class Web3jQueryException extends RuntimeException {

  public Web3jQueryException(String errMsg) {
    super(errMsg);
  }

  public Web3jQueryException(String errMsg, Throwable err) {
    super(errMsg, err);
  }
}
