package com.github.yhs0092.hello;

public interface Hello {
  String sayHello(String name) throws Exception;

  public String setStatus(String statusSwitch);

  public String getStatus();
}
