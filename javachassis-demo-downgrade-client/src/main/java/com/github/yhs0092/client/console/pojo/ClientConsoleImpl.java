package com.github.yhs0092.client.console.pojo;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.yhs0092.client.console.ClientConsole;
import com.github.yhs0092.client.reference.Hello;

import io.servicecomb.provider.pojo.RpcReference;
import io.servicecomb.provider.rest.common.RestSchema;

@RestSchema(schemaId = "clientconsole")
@RequestMapping(path = "/clientconsole", produces = MediaType.APPLICATION_JSON)
public class ClientConsoleImpl implements ClientConsole {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientConsoleImpl.class);

  @RpcReference(schemaId = "hello", microserviceName = "downgradeserver")
  private Hello hello;

  @RequestMapping(path = "/startRequest", method = RequestMethod.POST)
  public String startRequest(@RequestParam(name = "name") String name, @RequestParam(name = "times") int times) {
    LOGGER.info("start loop, name = [{}]", name);
    for (int i = 0; i < times; ++i) {
      LOGGER.info("invoke#[{}], name = [{}]", i, name);
      String result = null;
      try {
        result = hello.sayHello(name);
      } catch (Exception e) {
        LOGGER.error("catch an exception while invoking server...");
        e.printStackTrace();
      }
      LOGGER.info("invoke#[{}], name = [{}], result = [{}]", i, name, result);
    }
    LOGGER.info("done, name = [{}]", name);
    return "done";
  }
}
