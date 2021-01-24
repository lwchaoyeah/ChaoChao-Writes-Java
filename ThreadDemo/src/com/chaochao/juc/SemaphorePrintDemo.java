package com.chaochao.juc;

import java.util.concurrent.Semaphore;

/**
 * @description: 两个线程交替打印 0-100的奇数和偶数
 * @author Yoghourt
 * @date 2021/1/24 12:14
 * @version 1.0
 */

public class SemaphorePrintDemo {
    public static void main(String[] args) {
        PrintUsingSemaphore printUsingSemaphore = new PrintUsingSemaphore();
        Thread evenThread = new Thread(()->printUsingSemaphore.printEven());
        Thread oddThread = new Thread(()->printUsingSemaphore.printOdd());
        evenThread.start();
        oddThread.start();
    }

    public static class PrintUsingSemaphore{
        Semaphore evenSemaphore = new Semaphore(1);
        Semaphore oddSemaphore = new Semaphore(0);
        private Integer count = new Integer(0);

        public void printEven(){
            while(count<=100){
                try{
                    evenSemaphore.acquire();
                    System.out.println("evenThread: "+count++);
                    oddSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void printOdd(){
            while(count<=99){
                try{
                    oddSemaphore.acquire();
                    System.out.println("oddThread: "+count++);
                    evenSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
