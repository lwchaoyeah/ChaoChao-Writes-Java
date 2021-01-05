package com.chaochao.rpc.rpc6;

import com.chaochao.rpc.common.IUserService;

public class Client {
    public static void main(String[] args) {
        IUserService service = (IUserService) Stub.getStub(IUserService.class);
        System.out.println(service.findUserById(123));
    }
}
