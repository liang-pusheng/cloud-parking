package com.lps.cloudmember.controller;/**
 * member test api
 *
 * @author liangpusheng
 * @date 2021/8/10 20:25
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * member test api
 *
 * @author liangpusheng
 * @date 2021/08/10 20:25
 */
@RestController
public class MemberTestController {

  @GetMapping("/member/hello")
  public String hello() {
    return "member service";
  }
}
