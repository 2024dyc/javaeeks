package com.smartshop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * 缓存监控切面：
 * - 拦截 ProductService 的方法调用
 * - 计算耗时，判断是否命中缓存
 * - 快速响应（< 50ms）判定为 Redis 命中，慢响应（> 50ms）判定为 MySQL 查询
 */
@Aspect
@Component
public class CacheMonitorAspect {

    /**
     * ThreadLocal：存储当前请求的数据来源标识
     */
    public static final ThreadLocal<String> dataSource = new ThreadLocal<>();

    @Around("execution(* com.smartshop.service.ProductService.findByCondition(..))")
    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        Object result = pjp.proceed();
        long elapsed = Duration.between(start, Instant.now()).toMillis();

        String method = pjp.getSignature().getName();
        if (elapsed < 50) {
            // 响应极快 → Redis 缓存命中
            System.out.println("[CACHE HIT] " + method + " — 耗时 " + elapsed + "ms — 来源: Redis");
            dataSource.set("Redis");
        } else {
            // 响应较慢 → MySQL 查询
            System.out.println("[CACHE MISS] " + method + " — 耗时 " + elapsed + "ms — 来源: MySQL");
            dataSource.set("MySQL");
        }
        return result;
    }

    /**
     * 清除 ThreadLocal，防止内存泄漏
     */
    public static void clear() {
        dataSource.remove();
    }
}