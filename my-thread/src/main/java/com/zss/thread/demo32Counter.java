package com.zss.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/4 14:16
 */
public class demo32Counter {

    static int counter = 0;

    private static void counter(){
        synchronized(demo32Counter.class){
                counter++;
        }
    }

    static void m1()throws InterruptedException{
        int threadCount = 50;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() ->{
                try {
                    for (int j = 0; j <1000000; j++){
                        counter();
                    }
                }finally {
                    latch.countDown();
                }
            }).start();
        }
        long start = System.currentTimeMillis();
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s",counter,(end-start)));
    }

    public static void main(String[] args) throws InterruptedException{
        for (int i = 0; i <10; i++){
            counter = 0;
            m1();
        }
    }
}

class Demo32LongCounter{

    static AtomicLong counter = new AtomicLong(0L);

    static void counter(){
        counter.incrementAndGet();
    }

    static void m1() throws InterruptedException{
        int threadCount = 50;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++){
            new Thread(()->{
                try {
                    for (int j = 0; j <1000000; j++){
                        counter();
                    }
                }finally{
                    latch.countDown();
                }

            }).start();
        }
        long start = System.currentTimeMillis();
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s",counter.get(),(end-start)));
    }

    public static void main(String[] args) throws InterruptedException{
        for (int i = 0; i < 10; i++) {
            counter.set(0);
            m1();
        }
    }
}

class Demo32LongAdderCounter {

    static LongAdder counter = new LongAdder();

    static void counter() {
        counter.increment();
    }

    static void m1() throws InterruptedException {
        int threadCount = 50;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000000; j++) {
                        counter();
                    }
                } finally {
                    latch.countDown();
                }

            }).start();
        }
        long start = System.currentTimeMillis();
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", counter.sum(), (end - start)));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            counter.reset();
            m1();
        }
    }
}
    class Demo32LongAccumulatorCounter{

        static LongAccumulator counter = new LongAccumulator((x,y)->x+y,0L);

        static void counter(){
            counter.accumulate(1);
        }

        static void m1() throws InterruptedException{
            int threadCount = 50;
            CountDownLatch latch = new CountDownLatch(threadCount);
            for (int i = 0; i < threadCount; i++){
                new Thread(()->{
                    try {
                        for (int j = 0; j <1000000; j++){
                            counter();
                        }
                    }finally{
                        latch.countDown();
                    }

                }).start();
            }
            long start = System.currentTimeMillis();
            latch.await();
            long end = System.currentTimeMillis();
            System.out.println(String.format("结果：%s,耗时(ms)：%s",counter.longValue(),(end-start)));
        }

        public static void main(String[] args) throws InterruptedException{
            for (int i = 0; i < 10; i++) {
                counter.reset();
                m1();
            }
        }
}
