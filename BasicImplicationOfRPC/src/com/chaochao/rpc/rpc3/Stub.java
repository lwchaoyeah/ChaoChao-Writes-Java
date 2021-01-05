package com.chaochao.rpc.rpc3;

import com.chaochao.rpc.common.IUserService;
import com.chaochao.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    // 使用的动态代理是实现了UserService接口的新类
    public static IUserService getStub(){
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1",8888);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(123);

                s.getOutputStream().write(baos.toByteArray());
                s.getOutputStream().flush();

                DataInputStream dis = new DataInputStream(s.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();
                User user = new User(id,name);

                dos.close();
                s.close();
                return user;
            }
        };
        // 第一个参数：产生代理类的ClassLoader；第二个参数：代理实现的接口；第三个参数：调用处理器，自己实现！
        Object obj = Proxy.newProxyInstance(IUserService.class.getClassLoader(),new Class[]{IUserService.class},handler);
        // 测试代理是一个动态产生的新类
        System.out.println(obj.getClass().getName());
        System.out.println(obj.getClass().getInterfaces()[0]);
        return (IUserService)obj;
    }
}
