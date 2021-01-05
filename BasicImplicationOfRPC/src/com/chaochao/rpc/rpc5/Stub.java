package com.chaochao.rpc.rpc5;

import com.chaochao.rpc.common.IUserService;
import com.chaochao.rpc.common.User;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static IUserService getStub(){
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);

                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                String methodName = method.getName();
                Class[] parameterTypes = method.getParameterTypes();
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User user = (User) ois.readObject();

                oos.close();
                s.close();
                return user;
            }
        };
        Object obj = Proxy.newProxyInstance(IUserService.class.getClassLoader(),new Class[]{IUserService.class},handler);
        return (IUserService)obj;
    }
}