package com.dx.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author duanxiang 2019/6/6 14:45
 * 保证在修改对象引用时的线程安全性
 * Atmomic小结：
 * 1、可见性
 * 2、有序性
 * 3、原子性
 *
 * 1. volatile 修饰的变量能保证前两者
 * 2. CAS算法，也就是CPU级别的同步指令，相当于乐观锁，它可以检测到其他线程对共享数据的变化情况
 * 快速失败，放弃被污染的数据，保证当前线程修改的是正确的
 * 缺点：ABA问题
 * T1    T2
 * A     A->B->A
 * C
 * t1看起来A就是A，其实t2已经将A修改为B再修改为A，对Integer、Long这些类型还好没有副作用，如果是对一些数据结构就有副总用了
 *  AtomicReference<Stack> atomicReference = new AtomicReference<>();
 * STACK
 * A
 * B
 *
 * T1           T2
 * pop A        pop A
 *
 * pop B        pop B
 *
 *              push C
 *
 *              push A
 *
 *                t2先执行，t1看到栈顶是A 以为正确，在出栈A的时候，事实上B已经不在了，在的是C，这时就和预期情况不一致，产生并发问题，那是什么原因导致的呢？最终是 Stack需要通过节点之间的指针来连接，而Integer、Long并没有这些即不可变的。
 *                那么有没有什么办法解决这种ABA问题呢？
 *                在数据库中实现乐观锁的方式是加上一个版本号，同样在juc中也有AtomicStampedReference
 **/
public class AtomicReferenceTest {

    private Person person = new Person("zhangsan", 2);
    private AtomicReference<Person> atomicReference = new AtomicReference<>(person);

    @Test
    public void test() {
        atomicReference.compareAndSet(new Person("zhangsan", 2), new Person("lisi", 2));
        Person p = atomicReference.get();
        System.out.println(p);
        atomicReference.compareAndSet(p, new Person("lisi", 2));
        p = atomicReference.get();
        System.out.println(p);
    }

    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    static class Person {

        String name;
        int age;

    }
}
