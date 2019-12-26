package com.zss.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ThreadStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadStudyApplication.class, args);
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(3);
        Thread t1 = new T("t1",1,latch);
        t1.start();

        Thread t2 = new T("t2",2,latch);
        t2.start();

        Thread t3 = new T("t3",3,latch);
        t3.start();

        try {
            latch.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("我是主线程："+Thread.currentThread().getName()+"耗时："+(end-start)+"ms");

    }

    static class T extends Thread {
        int sleepSeconds;
        CountDownLatch latch;

        public T(String name,int sleepSeconds,CountDownLatch latch){
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.latch = latch;
        }

        @Override
        public void run() {
            try{
                Thread thread = Thread.currentThread();
                long start = System.currentTimeMillis();

                TimeUnit.SECONDS.sleep(sleepSeconds);
                long end = System.currentTimeMillis();
                System.out.println("我是线程："+thread.getName() + "休眠了几秒："+sleepSeconds+"，总耗时："+(end-start));
            }catch(InterruptedException e){
                e.printStackTrace();
            }finally{
                latch.countDown();
            }
            System.out.println("倒计时：" + latch.getCount());
        }
    }

}
