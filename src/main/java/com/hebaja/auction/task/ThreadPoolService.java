package com.hebaja.auction.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService {

    private static ExecutorService threadPool;

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public void create() {
        threadPool = Executors.newCachedThreadPool();
    }
}