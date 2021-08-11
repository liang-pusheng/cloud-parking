package com.lps.cloudcredit.controller;/**
 * credit test api
 *
 * @author liangpusheng
 * @date 2021/8/10 20:26
 */

import com.lps.cloudcredit.client.MemberFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * credit test api
 *
 * @author liangpusheng
 * @date 2021/08/10 20:26
 */
@RestController
public class CreditTestController {

  public static final Logger LOGGER = LoggerFactory.getLogger(CreditTestController.class);

  private final MemberFeignClient client;

  public CreditTestController(MemberFeignClient client) {
    this.client = client;
  }

  @GetMapping("/credit/test")
  public String test() {
    String hello = client.hello();
    int id = client.getById(10);
    LOGGER.info("【member id】 ==> {}", id);
    return "credit test " + hello;
  }

}
