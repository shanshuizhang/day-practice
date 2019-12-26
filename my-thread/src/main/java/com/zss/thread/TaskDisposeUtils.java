package com.zss.thread;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/2 11:28
 */
public class TaskDisposeUtils {

    public static final int POOL_SIZE;

    static{
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(),5);
    }

    /**
     * 多线程任务执行
     * @param taskList
     * @param consumer
     * @param <T>
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer)throws InterruptedException{
        dispose(true,POOL_SIZE,taskList,consumer);
    }

    /**
     * 多线程任务执行
     * @param moreThread 是否多线程
     * @param poolSize 线程数
     * @param taskList 任务列表
     * @param consumer 消费者
     * @param <T>
     */
    public static <T> void dispose(boolean moreThread,int poolSize,List<T> taskList, Consumer<T> consumer) throws InterruptedException{
        if(CollectionUtils.isEmpty(taskList)){
            return;
        }
        if(moreThread && poolSize > 1){
            poolSize = Math.min(poolSize,taskList.size());
            ExecutorService executor =  null;
            try{
                //executor = Executors.newFixedThreadPool(poolSize);
                executor = new ThreadPoolExecutor(poolSize,poolSize,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(10));
                CountDownLatch latch = new CountDownLatch(taskList.size());
                for(T t:taskList){
                    executor.execute(()->{
                        try{
                            consumer.accept(t);
                        }finally {
                            latch.countDown();
                        }
                    });
                }
                latch.await();
            }finally{
                if(executor != null){
                    executor.shutdown();
                }
            }
        }else{
            for(T item : taskList){
                consumer.accept(item);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        List<Integer> taskList = Stream.iterate(1,a->a+1).limit(10).collect(Collectors.toList());
        TaskDisposeUtils.dispose(taskList,item->{
            try{
                long start = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long end = System.currentTimeMillis();
                System.out.println(System.currentTimeMillis() + ",任务" + item + "执行完毕，耗时：" + (end - start));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        System.out.println(taskList + "中的任务都处理完毕");
    }
}
