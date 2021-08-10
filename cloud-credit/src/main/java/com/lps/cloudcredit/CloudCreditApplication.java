package com.lps.cloudcredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lps.cloudcredit.client")
@SpringBootApplication
public class CloudCreditApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudCreditApplication.class, args);
  }

}
