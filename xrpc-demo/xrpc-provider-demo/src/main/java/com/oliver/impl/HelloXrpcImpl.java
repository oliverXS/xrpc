package com.oliver.impl;

import com.oliver.HelloXrpc;

/**
 * @author xiaorui
 */
public class HelloXrpcImpl implements HelloXrpc {
    @Override
    public String sayHi(String message) {
        return "hi consumer" + message;
    }
}
