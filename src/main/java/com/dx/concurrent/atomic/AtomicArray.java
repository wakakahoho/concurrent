package com.dx.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.Assert;
import org.junit.Test;

/**包括：AtomicReferenceArray、AtomicLongArray、AtomicIntegerArray
 * @author duanxiang 2019/6/6 16:04
 * 修改数组中元素，出现的ABA问题和AtomicStampedReference一样也是将数据结构自己维护
 *
 *   private static final int base = unsafe.arrayBaseOffset(int[].class);
 *     private static final int shift;
 *     private final int[] array;
 *
 *      static {
 *         int scale = unsafe.arrayIndexScale(int[].class);
 *         if ((scale & (scale - 1)) != 0)
 *             throw new Error("data type scale not a power of two");
 *         shift = 31 - Integer.numberOfLeadingZeros(scale);
 *     }
 *
 *       private static long byteOffset(int i) {
 *         return ((long) i << shift) + base;
 *     }
 *     原理其实和设置Integer的原理一样都是调用的什么putIntxxx,compareAndSwapInt,不同的是需要找到数组在内存中的地址：
 *     base：基地址
 *     shift：偏移量
 *     数组元素的地址：base+shift
 **/
public class AtomicArray {

    @Test
    public void test(){
        //对数组元素的原子操作
        AtomicIntegerArray array = new AtomicIntegerArray(20);
        array.set(0,0);
        array.set(1,1);
        array.set(2,2);
        array.set(3,3);
        boolean b = array.compareAndSet(2, 2, 3);
        Assert.assertTrue(b);
    }


}
