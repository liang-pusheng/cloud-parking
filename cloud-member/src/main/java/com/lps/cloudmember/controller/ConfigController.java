package com.lps.cloudmember.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * nacos config center test controller
 *
 * 注解 @RefreshScope 实现配置自动更新
 * @author huangyao
 * @date 2021/8/11
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {


}
