package com.github.yhs0092.client.console;

public interface ClientConsole {
  /**
   *
   * @param name the param sent to server
   * @param times how many times to invoke server
   * @return result of invocation
   */
  String startRequest(String name, int times);
}
