package com.chaochao.rpc.rpc6;

import com.chaochao.rpc.common.IProductService;
import com.chaochao.rpc.common.IUserService;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static boolean running = true;
    private static HashMap<String,Class> registerTable = new HashMap<>();
    static{
        registerTable.put(IUserService.class.getName(),UserServiceImpl.class);//key类型是接口，value是具体实现类才能完成调用
        registerTable.put(IProductService.class.getName(), ProductServiceImpl.class);
    }

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        while (running){
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    public static void process(Socket socket) throws Exception {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        ObjectInputStream ois = new ObjectInputStream(in);

        //为了适应客户端通用化而做的改动
        String clazzName = ois.readUTF();
        String methodName = ois.readUTF();
        Class[] parameterTypes = (Class[]) ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        //IUserService service = new IUserServiceImpl();
        //本来是硬编码new出来的，现在变成从注册表中查到服务类，如果使用spring甚至还可以直接根据配置注入bean然后根据bean查找!
        Object service = registerTable.get(clazzName).newInstance();

        //Object service = registerTable.get(clazzName).newInstance();
        Method method = service.getClass().getMethod(methodName, parameterTypes);
        Object o = method.invoke(service, args);

        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(o);
        oos.flush();
    }
}
