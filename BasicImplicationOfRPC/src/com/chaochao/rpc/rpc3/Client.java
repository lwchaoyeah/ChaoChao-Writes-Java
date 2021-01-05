package com.chaochao.rpc.rpc3;

import com.chaochao.rpc.common.IUserService;

/**
 * 使用动态代理模式，自动添加网络IO细节
 */
public class Client {
    public static void main(String[] args) {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(123));
    }
}
