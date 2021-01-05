package com.chaochao.rpc.rpc6;

import com.chaochao.rpc.common.IProductService;
import com.chaochao.rpc.common.Product;

public class ProductServiceImpl implements IProductService{
    @Override
    public Product findProductByName(String name) {
        return new Product(1,name,1);
    }
}
