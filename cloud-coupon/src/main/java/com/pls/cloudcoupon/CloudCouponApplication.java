package com.pls.cloudcoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liangpusheng
 * @date 2021/8/12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudCouponApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudCouponApplication.class, args);
  }

}
