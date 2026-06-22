package com.assignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Task3 {

    public class BankStatementBatchProcessor {

            //Replaced int with AtomicInteger because processedCount++ is not thread-safe and may cause race conditions.
            private AtomicInteger processedCount = new AtomicInteger(0);

            public void process(List<StatementRecord> records) {

                ExecutorService executor = Executors.newFixedThreadPool(10);

                for (StatementRecord record : records) {

                    executor.submit(() -> {
                        processRecord(record);

                        //Use atomic increment to ensure thread-safe counter updates.
                        processedCount.incrementAndGet();
                    });
                }

                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.MINUTES);
            }

            public int getProcessedCount() {

                //Return the current value from AtomicInteger.
                return processedCount.get();
            }
        }
}
