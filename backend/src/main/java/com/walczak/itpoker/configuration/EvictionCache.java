package com.walczak.itpoker.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EvictionCache {

    private final LoadingCache<String, String> cache;
    private final Long durationInMinutes;
    private static EvictionCache instance;
    private boolean isScheduled = false;
    private ScheduledFuture<?> scheduledTask;

    public static EvictionCache getInstance() {
        if (instance != null) {
            return instance;
        }
        throw new IllegalStateException("Scheduler not initialized");
    }

    public static EvictionCache getInstance(Long evictionTimeInMinutes, Long refreshCacheTimeInMinutes, EvictionAction action) {
        if (instance != null) {
            return instance;
        }
        synchronized (EvictionCache.class) {
            if (instance == null) {
                instance = new EvictionCache(evictionTimeInMinutes, refreshCacheTimeInMinutes, action);
            }
            return instance;
        }
    }

    public EvictionCache(Long evictionTimeInMinutes, Long refreshCacheTimeInMinutes, EvictionAction action) {

        this.durationInMinutes = refreshCacheTimeInMinutes;
        CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String o) {
                return o;
            }
        };

        RemovalListener<String, String> listener = (notification) -> {
            if (notification.getCause() == RemovalCause.EXPIRED) {
                action.evict(notification.getKey());
            }
            stopSchedulingIfCacheIsEmpty();
        };

        this.cache = CacheBuilder.newBuilder()
                .removalListener(listener)
                .expireAfterAccess(evictionTimeInMinutes, TimeUnit.MINUTES)
                .build(cacheLoader);
    }

    public void remove(String id) {
        this.cache.invalidate(id);
        stopSchedulingIfCacheIsEmpty();
    }

    private void stopSchedulingIfCacheIsEmpty() {
        if(this.cache.asMap().isEmpty()) {
            scheduledTask.cancel(true);
            isScheduled = false;
            System.out.println("+++++++++++++++++++++++++++++++++stop schedule+++++++++++++++++++++++++++++++++");
        }
    }

    public void put(String id) {
        this.cache.put(id, id);
    }

    public void refresh(String id) {
        this.cache.getIfPresent(id);
    }

    private void cleanUp() {
        System.out.println(cache.asMap());
        this.cache.cleanUp();
    }

    public void scheduleCleaningCache() {
        System.out.println("+++++++++++++++++++++++++++++++++schedule+++++++++++++++++++++++++++++++++");
        if (!isScheduled) {
            scheduledTask = Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(this::cleanUp, durationInMinutes, durationInMinutes, TimeUnit.MINUTES);
            isScheduled = true;
        }
    }

    public String getCache() {
        return cache.asMap().toString();
    }

}
