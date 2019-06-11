package com.dx.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * @author duanxiang 2019/6/10 17:42
 * A synchronization aid that allows one or more threads to wait until
 *  * a set of operations being performed in other threads completes.
 **/
public class CountDownLatchTest {

    @Test //将能够并行的部分并行化
    public void test() {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("start init");
            try {
                latch.await();
                System.out.println("load data completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("quering data....");
            System.out.println("end query");
            latch.countDown();
        }).start();


    }

    @Test//将任务划分为多个部分并行执行
    public void test1() throws InterruptedException {
        int numOfTask = 5;
        CountDownLatch countDownLatch = new CountDownLatch(numOfTask);
        ExecutorService executorService = Executors.newFixedThreadPool(numOfTask);
        for (int i = 0; i < numOfTask; i++) {
            executorService.submit(()->{
                System.out.println("run task");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
//        countDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println("run completed");
        executorService.shutdown();

    }
}
