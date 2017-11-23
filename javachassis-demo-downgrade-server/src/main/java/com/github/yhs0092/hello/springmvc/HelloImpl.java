package com.github.yhs0092.hello.springmvc;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.yhs0092.hello.Hello;

import io.servicecomb.provider.rest.common.RestSchema;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON)
public class HelloImpl implements Hello {
  private StatusSwitch statusSwitch = StatusSwitch.NORMAL;

  private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

  public static final String HELLO_PREFIX = "Hello, ";

  @RequestMapping(path = "/sayHello", method = RequestMethod.GET)
  @Override
  public String sayHello(@RequestParam(value = "name") String name) throws Exception {
    LOGGER.info("sayHello is called, name = {}, status = {}", name, statusSwitch);
    switch (statusSwitch) {
      case NORMAL:
        return HELLO_PREFIX + name;
      case TIME_OUT: {
        Thread.sleep(1000 * 2);
        return HELLO_PREFIX + name;
      }
      case THROW_EXCEPTION:
        throw new Exception("sample exception");
      default:
        throw new Exception("unexpected status");
    }
  }

  @RequestMapping(path = "/status", method = RequestMethod.PUT)
  @Override
  public String setStatus(@RequestParam(name = "status") String statusSwitch) {
    LOGGER.info("set status to {}", statusSwitch);
    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    return "OK";
  }

  @RequestMapping(path = "/status", method = RequestMethod.GET)
  @Override
  public String getStatus() {
    return this.statusSwitch.toString();
  }
}
