package com.chaochao.rpc.rpc1;

import com.chaochao.rpc.common.IUserService;
import com.chaochao.rpc.common.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 最原始的rpc调用方式，与底层网络IO耦合
 */
public class Server {
    private static  boolean running = true;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        while (running){
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws IOException {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        // 数据流进行封装
        DataInputStream dis = new DataInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        int id = dis.readInt();
        IUserService service = new UserServiceImpl();
        User user = service.findUserById(id);
        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
