package com.dx.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author duanxiang 2019/6/6 15:37 因为AtomicReference将类型交给外部调用方，导致类型不是不可变的，最终出现ABA问题， 要是在内部实现一个数据结构用来承载外部类型，并且加上一个版本号来检查承载的类型是否已经改变
 * 这就是AtomicStampedReference的原理。
 **/
public class AtomicStampedReferenceTest {

    private AtomicStampedReference<Integer> atomicRef = new AtomicStampedReference<>(1, 1);

    @Test //模拟t1线程
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {

            int stamp = atomicRef.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean success = atomicRef.compareAndSet(1, 2, stamp, stamp + 1);
            //false 修改失败
            System.out.println(atomicRef.getReference() + "-" + atomicRef.getStamp());

            Assert.assertTrue(success);
        });

        Thread t2 = new Thread(() -> {
            int stamp = atomicRef.getStamp();
            boolean success = atomicRef.compareAndSet(1, 3, stamp, stamp + 1);
            System.out.println(atomicRef.getReference() + "-" + atomicRef.getStamp());
            stamp = atomicRef.getStamp();
            boolean success2 = atomicRef.compareAndSet(3, 1, stamp, stamp + 1);
            System.out.println(atomicRef.getReference() + "-" + atomicRef.getStamp());
            Assert.assertTrue(success2);
            Assert.assertTrue(success);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
