package com.zss.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/2 18:13
 */
public class BlockingQueueDemo<E> {

    int queueSize;

    public BlockingQueueDemo(int queueSize) {
        this.queueSize = queueSize;
    }

     ReentrantLock lock = new ReentrantLock();

    /**
     * 判断队列满时等待条件
     */
     Condition notFull = lock.newCondition();

    /**
     * 判断队列空时等待条件
     */
     Condition notEmpty = lock.newCondition();

    LinkedList<E> list = new LinkedList();

    void enqueue(E e){
        lock.lock();
        try{
            while (queueSize == list.size()) {
                notFull.await();
            }
            list.add(e);
            System.out.println("入队："+e);
            notEmpty.signal();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    E dequeue(){
        E e;
        lock.lock();
        try{
            while (list.size() == 0) {
                notEmpty.await();
            }
            e = list.removeFirst();
            System.out.println("出队："+e);
            notFull.signal();
            return e;
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }finally{
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        BlockingQueueDemo<Integer> demo = new BlockingQueueDemo<>(1);
        for(int i = 0; i < 10; i++){
            int v = i;
            new Thread(()->{
                demo.enqueue(v);
            }).start();
        }
        for(int i = 0; i < 10; i++){
            new Thread(()->{
                Integer v = demo.dequeue();
                System.out.println("v=="+v);
            }).start();
        }
    }
}
