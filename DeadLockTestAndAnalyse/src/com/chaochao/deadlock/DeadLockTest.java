package com.chaochao.deadlock;

/**
 * @description: 死锁测试
 * @author Yoghourt
 * @date 2021/1/10 16:27
 * @version 1.0
 */

public class DeadLockTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new DeadLock());
        Thread thread2 = new Thread(new DeadLock());
        thread1.start();
        thread2.start();
    }
}
