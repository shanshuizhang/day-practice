package com.zss.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/4 11:13
 */
public class Demo31 {

    static int result = 0;
    public static void main(String[] args)throws InterruptedException{

        Thread t = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                result = 2;
            } catch (InterruptedException e) {

            }
        });
        t.start();
        long start = System.currentTimeMillis();
        t.join();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)+",,result=="+result);
    }
}

class CountDownLatchResultTest{

    static int result = 0;
    static AtomicInteger count = new AtomicInteger();
    private static CountDownLatch latch = new CountDownLatch(1000);
    public static void main(String[] args) throws InterruptedException{

        for(int i=1; i<=1000; i++){
            int data = i;
            new Thread(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(data);
                    result = count.incrementAndGet();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            }).start();
        }

        long start = System.currentTimeMillis();
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+ (end-start)+"ms,result="+result);
    }
}
