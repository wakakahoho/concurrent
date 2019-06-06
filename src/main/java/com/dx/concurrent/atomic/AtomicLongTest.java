package com.dx.concurrent.atomic;

/**
 * @author duanxiang 2019/6/6 14:35
 **/
public class AtomicLongTest {
    /**
     * Records whether the underlying JVM supports lockless
     * compareAndSwap for longs. While the Unsafe.compareAndSwapLong
     * method works in either case, some constructions should be
     * handled at Java level to avoid locking user-visible locks.
     *
     * 32
     * long  64  long型的原子性
     * high 32
     * low 32
     */
    //static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8(); //看JVM是否支持
}
