package com.chaochao.deadlock;

/**
 * @description: 编写死锁Demo，并通过jps+jstack、jvisualvm等来检测
 * @author Yoghourt
 * @date 2021/1/10 15:41
 * @version 1.0
 */

import java.util.concurrent.atomic.AtomicInteger;

public class DeadLock implements Runnable{
    private final static String A = "A";
    private final static String B = "B";
    private final static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        if(count.intValue()==0){
            count.incrementAndGet();
            synchronized (A){
                System.out.println(Thread.currentThread().getName()+": I hava got "+A);
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+ ": I am going to get "+B);
                synchronized (B){
                    System.out.println(Thread.currentThread().getName() + ": I hava got "+B);
                }
            }
        }else {
            synchronized (B){
                System.out.println(Thread.currentThread().getName()+": I hava got "+B);
                System.out.println(Thread.currentThread().getName()+ ": I am going to get "+A);
                synchronized (A){
                    System.out.println(Thread.currentThread().getName() + ": I hava got "+A);
                }
            }
        }

    }
}
