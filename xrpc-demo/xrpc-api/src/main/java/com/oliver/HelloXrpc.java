package com.oliver;

/**
 * @author xiaorui
 */
public interface HelloXrpc {
    /**
     * Common interface, both server and client need to be dependent.
     * @param message Specific message
     * @return result
     */
    String sayHi(String message);
}
