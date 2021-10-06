package com.amir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Result: occupy almost 70% cpu in core i7 9700 processor
 */
public class CpuUsageCheckForSingleBlockingTask {
    private static final ExecutorService executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Void>> topicFutures = new ArrayList<>();
        //runAsync -> always returns Void, supplyAsync -> returns completableFuture<Boolean> in this case
        IntStream.rangeClosed(1, 8).forEach(num -> topicFutures.add(CompletableFuture.runAsync(() ->  {
            while (true) {
                System.out.println(" ");
            }
        }, executor)));

        CompletableFuture<Void> allTopicFutures = CompletableFuture.allOf(topicFutures.toArray(new CompletableFuture[topicFutures.size()]));

        //The CompletableFuture. get() method is blocking. It waits until the Future is completed and returns the result after its completion
        allTopicFutures.get();
    }
}
