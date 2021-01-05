package com.chaochao.rpc.rpc5;

import com.chaochao.rpc.common.IUserService;
import com.chaochao.rpc.common.User;
import com.chaochao.rpc.rpc1.UserServiceImpl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

// unchanged
public class Server {
    private static  boolean running = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ServerSocket ss = new ServerSocket(8888);
        while (running){
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        // 数据流进行封装
        ObjectInputStream ois = new ObjectInputStream(in);
        ObjectOutputStream oos = new ObjectOutputStream(out);

        String methodName = ois.readUTF();
        Class[] parameterTypes = (Class[])ois.readObject();
        Object[] args = (Object[])ois.readObject();

        IUserService service = new UserServiceImpl();
        Method method = service.getClass().getMethod(methodName,parameterTypes);
        User user = (User)method.invoke(service,args);

        // 返回值用Object封装，支持任意类型
        oos.writeObject(user);
        oos.flush();
    }
}