package com.dx.concurrent.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author duanxiang 2019/6/5 17:45
 **/
public class AtomicIntegerTest {

    /**
     * 1.可见性 2.顺序性（内存屏障）：　　1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
     * 2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。
     * 3.不能保证原子性
     */
    private volatile int value = 0;
    /**
     * 可见性、顺序性 由AtomicInteger的value被volatile修饰保证
     * 原子性由
     *  public final int getAndAddInt(Object var1, long var2, int var4) {
     *         int var5;
     *         do {
     *             var5 = this.getIntVolatile(var1, var2);  //步骤1 保证不重排序、内存可见性
     *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));//步骤2 CAS快速失败重试
     *
     *         return var5;
     *     }
     *     while+CAS保证，获取volatile变量的值，compareAndSwapInt判断是否被修改，如果被修改进入下次循环，再次读取volatile的值，直到步骤1和步骤2的var5在内存中是相等的，即没有被修改过，才更新。然后修改成功退出循环
     */
    private AtomicInteger integer = new AtomicInteger(0);


    @Test
    public void testVolatile() throws InterruptedException {
        //无原子性
        List<Thread> threadList = new ArrayList<>();

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Thread t = new Thread(() -> value += 5);
            threadList.add(t);
            t.start();
        });
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println(value);
        Assert.assertTrue(500 == value);//不总是true
    }

    @Test
    public void testAtomicInteger() throws InterruptedException {
        //保证原子性
        List<Thread> threadList = new ArrayList<>();

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Thread t = new Thread(() -> integer.getAndAdd(5));
            threadList.add(t);
            t.start();
        });
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println(integer.get());
        Assert.assertTrue(500 == integer.get());
    }
}
