package com.ynzz.lab.chapter01.gateway;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 第 1 讲最小内存限流。
 */
public class RateLimitService {
    private final int maxRequestsPerTenant;
    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<String, Counter>();

    public RateLimitService(int maxRequestsPerTenant) {
        this.maxRequestsPerTenant = maxRequestsPerTenant;
    }

    public void check(String tenantId, String operatorId) {
        String key = tenantId == null ? "UNKNOWN" : tenantId;
        long now = System.currentTimeMillis();
        Counter counter = counters.get(key);
        if (counter == null) {
            counter = new Counter(now, 0);
            counters.put(key, counter);
        }

        synchronized (counter) {
            if (now - counter.windowStartMs >= 1000L) {
                counter.windowStartMs = now;
                counter.count = 0;
            }
            counter.count++;
            if (counter.count > maxRequestsPerTenant) {
                throw new IllegalStateException("rate limit exceeded for tenant=" + key);
            }
        }
    }

    private static class Counter {
        private long windowStartMs;
        private int count;

        private Counter(long windowStartMs, int count) {
            this.windowStartMs = windowStartMs;
            this.count = count;
        }
    }
}
