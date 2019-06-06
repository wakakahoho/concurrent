package com.dx.concurrent.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.junit.Assert;
import org.junit.Test;

/**
 * AtomicReferenceFieldUpdater、AtomicIntegerFieldUpdater、AtomicLongFieldUpdater
 * @author duanxiang 2019/6/6 16:35 原理：反射找到字段，通过unsafe找到字段的偏移量，然后用cas设置值。
 *
 * 使用场景：多个线程修改一个对象中的字段，但又不希望给这个对象的方法加上锁
 **/
public class FieldUpdaterTest {

    volatile int a = 0; //必须是一个volatile 否则会抛出异常
    volatile int b = 0;

    AtomicIntegerFieldUpdater<FieldUpdaterTest> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(FieldUpdaterTest.class, "a");

    //线程安全的，无锁的对外暴露
    public void setB(int b){
        fieldUpdater.getAndSet(this,b);
    }

    @Test
    public void test() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 200; i++) {

            Thread t = new Thread(() -> {
                fieldUpdater.addAndGet(this,5);
                b =b+5;
            });
            threadList.add(t);
            t.start();

        }
        for (Thread t : threadList) {
            t.join();
        }
        Assert.assertEquals(1000, fieldUpdater.get(this)); //不会失败
        Assert.assertEquals(1000, b); //可能失败


    }


}
