package com.pls.cloudcoupon.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 优惠券API
 *
 *
 * <p>
 * redis锁存在的一些问题
 * 1、客户端获取锁成功后崩溃，锁未释放导致其他客户端获取不到锁
 * 解决方法：为锁设置过期时间
 * <p>
 * 2、setnx与expire非原子操作
 * 解决办法：redis2.6.12后支持两者操作为原子操作
 * <p>
 * 3、锁误解除，线程 A 成功获取到了锁，并且设置了过期时间 30 秒，
 * 但线程 A 执行时间超过了 30 秒，锁过期自动释放，此时线程 B 获取到了锁；
 * 随后 A 执行完成，线程 A 使用 DEL 命令来释放锁，但此时线程 B 加的锁还没有执行完成，
 * 线程 A 实际释放的线程 B 加的锁。
 * 解决办法：通过在 value 中设置当前线程加锁的标识，在删除之前验证 key 对应的 value 判断锁是否是当前线程持有。
 * <p>
 * 4、超时解锁导致并发，如果线程 A 成功获取锁并设置过期时间 30 秒，但线程 A 执行时间超过了 30 秒，
 * 锁过期自动释放，此时线程 B 获取到了锁，线程 A 和线程 B 并发执行。
 * 解决办法：更好的方法是是使用Redission，WatchDog机制会为将要过期但未释放的锁增加有效时间。
 *
 * @author liangpusheng
 * @date 2021/8/12
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

  private final RedisTemplate<String, String> redisTemplate;

  public CouponController(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 使用Redisson解决超时解锁导致的并发问题
   * Redisson底层的实现原理就是使用lua脚本，因为lua脚本在redis中的执行是原子的。
   *
   * @return 执行状态
   */
  @GetMapping("/redisson/kill")
  public String killCoupon() throws InterruptedException {
    RedissonClient redissonClient = Redisson.create();
    RLock rLock = redissonClient.getLock("lock");
    boolean result = rLock.tryLock(100, 100, TimeUnit.SECONDS);
    if (result) {
      try {
        createOrder();
        decrementStock();
      } finally {
        rLock.unlock();
      }
      return "success";
    }
    return "fail";
  }


  /**
   * redis锁伪代码
   * 实际开发中使用Redisson来实现加锁解锁的操作
   *
   * @return 执行状态
   */
  @GetMapping("/kill")
  public String secKillCoupon() {
    String key = "nx_key";
    // 声明一个有标识的key
    String markedValue = "only_value";
    if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, markedValue, 10, TimeUnit.SECONDS))) {
      try {
        createOrder();
        decrementStock();
      } finally {
        // 通过value判断如果是同一个线程的才允许删除key
        if (markedValue.equals(redisTemplate.opsForValue().get(key))) {
          redisTemplate.opsForValue().getOperations().delete(key);
        }
      }
      return "success";
    } else {
      return "fail";
    }
  }

  private void createOrder() {
  }

  private void decrementStock() {
  }


  @GetMapping("/consume")
  public Object consumeCoupon() {
    redisTemplate.opsForValue().set("test-key", "test-value");
    LOGGER.info("【set完毕】");

    return redisTemplate.opsForValue().get("test-key");
  }

}
