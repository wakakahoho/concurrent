package com.dx.concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

/**
 * @author duanxiang 2019/6/6 14:10
 **/
public class AtomicBooleanTest {
    //和AtomicInteger实现方式一样，1表示true，0表示 false


    private boolean flag1 = true;
    private volatile boolean flag2 = true;
    private AtomicBoolean flag3 = new AtomicBoolean(true);

    @Test  //永远不会停止
    public void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag1) {

            }
            System.out.println(Thread.currentThread().getName() + " exit");
        });
        thread.start();
        Thread.sleep(2000);
        flag1 = false;
        thread.join();



    }

    @Test  //可以停止
    public void test1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag1) {
                // public void println(String x) {
                //        synchronized (this) {
                //            print(x);
                //            newLine();
                //        }
                //    }
                System.out.println("i am working");
            }
            System.out.println(Thread.currentThread().getName() + " exit");
        });
        thread.start();
        Thread.sleep(2000);
        flag1 = false;
        thread.join();



    }

    @Test //2s 停止
    public void test2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag2) {

            }
            System.out.println(Thread.currentThread().getName() + " exit");
        });
        thread.start();
        Thread.sleep(2000);
        flag2 = false;
        thread.join();


    }
    @Test
    public void test3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag3.get()) {

            }
            System.out.println(Thread.currentThread().getName() + " exit");
        });
        thread.start();
        Thread.sleep(2000);
        flag3.getAndSet(false);
        thread.join();


    }
}
