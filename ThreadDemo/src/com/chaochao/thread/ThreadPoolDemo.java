package com.chaochao.thread;

import java.util.concurrent.*;

/**
 * @description: ThreadPool.submit()返回值，由FutureTask.get()接收
 * @author Yoghourt
 * @date 2021/1/9 20:44
 * @version 1.0
 */

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MyCallable myCallable = new MyCallable();
        Future<String> task = executorService.submit(myCallable);
        if(!task.isDone()){
            System.out.println("task has not finished, please wait!");
        }
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
