package com.zss.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyThreadApplicationTests {

    @Test
    void contextLoads() {
        int a =1;
        boolean flag = (a & 2) == 2;
        System.out.println(flag);
        /*Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();
       Thread t1 = new Thread(new ThreadTest(obj1,obj2,true));
       t1.setName("thread1--线程1");
       t1.start();

        Thread t2 = new Thread(new ThreadTest(obj1,obj2,false));
        t2.setName("thread2--线程2");
        t2.start();*/
    }
    @Data
    @AllArgsConstructor
    public static class ThreadTest implements Runnable{
        Obj1 obj1;
        Obj2 obj2;
        //int a,b;
        boolean flag;

        @Override
        public void run() {
            try{
                if (flag) {
                    synchronized (obj1) {
                        System.out.println("方法一1执行");
                            Thread.sleep(100);
                        synchronized (obj2) {
                            System.out.println("方法一执行");
                        }
                    }
                } else {
                    synchronized (obj2) {
                        System.out.println("方法2执行");
                            Thread.sleep(100);
                        synchronized (obj1) {
                            System.out.println("方法二执行");
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class Obj1{}

    public static class Obj2{}
}
