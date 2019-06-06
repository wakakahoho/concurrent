package com.dx.concurrent.atomic;

import java.lang.reflect.Field;

import org.junit.Test;

import sun.misc.Unsafe;

/**
 * @author duanxiang 2019/6/6 17:09
 *
 **/
public class UnsafeTest {

    @Test
    public void test(){
        /**
         * java.lang.SecurityException: Unsafe
         */
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);
    }

    @Test
    public void testGetUnsafe() throws NoSuchFieldException, IllegalAccessException {
        System.out.println(getUnsafe());
    }
    //反射获取 Unsafe
    public Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe)theUnsafe.get(null);
    }



}
