package com.chaochao.rpc.rpc1;

import com.chaochao.rpc.common.IUserService;
import com.chaochao.rpc.common.User;

public class UserServiceImpl implements IUserService {

    @Override
    public User findUserById(Integer id) {
        return new User(id,"Alice");
    }
}
