package com.lps.cloudcredit.client;/**
 * member feign api
 *
 * @author liangpusheng
 * @date 2021/8/10 20:54
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * member feign api
 *
 * @author liangpusheng
 * @date 2021/08/10 20:54
 */
@FeignClient(value = "cloud-member")
public interface MemberFeignClient {

  @GetMapping("/member/hello")
  String hello();
}
