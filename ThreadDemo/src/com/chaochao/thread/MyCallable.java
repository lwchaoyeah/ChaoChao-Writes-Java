package com.chaochao.thread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable{

    @Override
    public Object call() throws Exception {
        for (int i=0; i<10; i++){
            System.out.println("Callable: "+i);
            Thread.sleep(5000);
            // 主动判断中断状态
            if(Thread.interrupted()){
                return Thread.currentThread().getName() + " is interrupted.";
            }
        }
        return Thread.currentThread().getName() + " is completed."; // 返回值
    }
}
