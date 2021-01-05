package com.chaochao.rpc.rpc1;

import com.chaochao.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(123);

        s.getOutputStream().write(baos.toByteArray());
        s.getOutputStream().flush();

        DataInputStream dis = new DataInputStream(s.getInputStream());
        // 读取顺序与服务端写入顺序一致！
        int id = dis.readInt();
        String name = dis.readUTF();
        User user = new User(id,name);

        System.out.println(user);
        dos.close();
        s.close();
    }
}
