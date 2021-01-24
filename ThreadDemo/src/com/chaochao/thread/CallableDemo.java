package com.chaochao.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description: 实现Callable接口重写call()方法来实现线程,FutureTask接受call()方法返回值
 * @author Yoghourt
 * @date 2021/1/9 19:48
 * @version 1.0
 */

public class CallableDemo{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        Thread thread = new Thread(task);
        thread.start();
        if(!task.isDone()){
            System.out.println("task has not finished, please wait!");
        }
        Thread.sleep(5000);
        try {
            thread.interrupt();
            System.out.println("task return: " + task.get());
        }catch (ExecutionException e){
            System.out.println("ExecutionException:");
            e.printStackTrace();
        }catch (InterruptedException e){
            System.out.println("InterruptedException:");
            e.printStackTrace();
        }
    }
}
