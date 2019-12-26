package com.zss.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/2 10:54
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch firstLatch = new CountDownLatch(1);
        CountDownLatch secondLatch = new CountDownLatch(3);
        long start = System.currentTimeMillis();
        Thread t1 = new T("t1",2,firstLatch,secondLatch);
        t1.start();

        Thread t2 = new T("t2",3,firstLatch,secondLatch);
        t2.start();

        Thread t3 = new T("t3",5,firstLatch,secondLatch);
        t3.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            firstLatch.countDown();
        }

        try {
            secondLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("线程："+Thread.currentThread().getName()+"总耗时："+(end-start));
    }

    static class T extends Thread {
        int sleepSecond;
        CountDownLatch firstLatch;
        CountDownLatch lastLatch;

        public T(String name,int sleepSecond, CountDownLatch firstLatch, CountDownLatch lastLatch) {
            super(name);
            this.sleepSecond = sleepSecond;
            this.firstLatch = firstLatch;
            this.lastLatch = lastLatch;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Thread tc = Thread.currentThread();
            System.out.println(start + "线程："+tc.getName()+"开始等待枪响");
            try {
                firstLatch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("线程开始跑："+tc.getName()+"");
            try {
                TimeUnit.SECONDS.sleep(sleepSecond);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                lastLatch.countDown();
            }
            long end = System.currentTimeMillis();
            System.out.println("线程："+tc.getName()+"耗时："+(end-start));

        }
    }


}
