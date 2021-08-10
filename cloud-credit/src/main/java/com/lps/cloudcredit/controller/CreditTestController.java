package com.lps.cloudcredit.controller;/**
 * credit test api
 *
 * @author liangpusheng
 * @date 2021/8/10 20:26
 */

import com.lps.cloudcredit.client.MemberFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private MemberFeignClient client;

  @GetMapping("/credit/test")
  public String test() {
    String hello = client.hello();
    return "credit test " + hello;
  }

}
