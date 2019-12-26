package com.zss.thread;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/4 15:23
 */
public class GuavaTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        try{
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(executor);
            ListenableFuture<Integer> future = executorService.submit(() ->{
                System.out.println(System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(System.currentTimeMillis());
                return 10;
            });
            future.addListener(()->{
                System.out.println("任务执行完毕，回调成功了");
            },MoreExecutors.directExecutor());
            System.out.println("结果："+future.get());
        }finally{
            executor.shutdown();
        }
    }
}
class GuavaTest2{
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try{
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(executor);
            ListenableFuture<Integer> future = executorService.submit(() ->{
                System.out.println(System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
                int i = 1/0;
                System.out.println(System.currentTimeMillis());
                return 10;
            });
            Futures.addCallback(future, new FutureCallback<Integer>() {
                @Override
                public void onSuccess(@Nullable Integer integer) {
                    System.out.println("执行成功了"+integer);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(String.format("执行发生异常：%s,异常：%s,",throwable.getMessage(),throwable));
                }
            },MoreExecutors.directExecutor());
            System.out.println("结果："+future.get());
        }finally{
            executor.shutdown();
        }
    }
}
