package com.amir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Sample code to check completable future.
 *
 */
public class CompletableFutureAllOff {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List a = Collections.synchronizedList(new ArrayList());
        List<CompletableFuture<Void>> topicFutures = new ArrayList<>();
        //runAsync -> always returns Void, supplyAsync -> returns completableFuture<Boolean> in this case
        IntStream.rangeClosed(1, 999).forEach(num -> topicFutures.add(CompletableFuture.runAsync(() -> a.add(num), executor)));

        CompletableFuture<Void> allTopicFutures = CompletableFuture.allOf(topicFutures.toArray(new CompletableFuture[topicFutures.size()]));

        //The CompletableFuture. get() method is blocking. It waits until the Future is completed and returns the result after its completion
        allTopicFutures.get();

        System.out.println("Done. Array Size: " + a.size());
    }
}
