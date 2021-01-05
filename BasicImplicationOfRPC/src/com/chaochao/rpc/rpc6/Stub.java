package com.chaochao.rpc.rpc6;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static Object getStub(Class clazz){
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);

                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                String clazzName = clazz.getName();
                String methodName = method.getName();
                Class[] parameterTypes = method.getParameterTypes();

                oos.writeUTF(clazzName);
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                //接收服务端返回的结果,object读入
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Object obj = ois.readObject();

                oos.close();
                s.close();
                return obj;//返回通用对象
            }
        };
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},handler);
        return obj;
    }
}
