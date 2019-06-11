package com.dx.concurrent.atomic;

import java.lang.reflect.Field;

import org.junit.Test;

import sun.misc.Unsafe;

/**
 * @author duanxiang 2019/6/6 17:09
 *绕过构造函数、给私有变量赋值、加载类
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
   static class Simple {

        private int i = 0;


        static {
            System.out.println("static init");
        }
        Simple(){
            i = 10;
            System.out.println("init");
        }

        public int get(){
            return i;
        }
    }

    @Test //绕过构造函数
    public void testBypassInit() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Unsafe unsafe = getUnsafe();
        Simple simple = (Simple)unsafe.allocateInstance(Simple.class);
        System.out.println(simple.get());
        System.out.println(simple.getClass().getClassLoader());

    }

    @Test //给私有变量赋值
    public void setPrivate() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        Simple simple = new Simple();
        Field field = Simple.class.getDeclaredField("i");
        unsafe.putInt(simple,unsafe.objectFieldOffset(field),20);
        System.out.println(simple.get());
    }



}
