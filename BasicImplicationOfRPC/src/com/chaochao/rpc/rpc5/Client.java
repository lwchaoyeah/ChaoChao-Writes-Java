package com.chaochao.rpc.rpc5;

import com.chaochao.rpc.common.IUserService;

public class Client {
    public static void main(String[] args) {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(123));
    }
}
