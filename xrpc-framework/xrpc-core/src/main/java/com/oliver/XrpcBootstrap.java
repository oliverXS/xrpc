package com.oliver;

import java.util.List;

/**
 * @author xiaorui
 */
public class XrpcBootstrap {
    /**
     * XrpcBootstrp is a singleton, and each application has only one instance.
     */
    private static XrpcBootstrap xrpcBootstrap = new XrpcBootstrap();

    private XrpcBootstrap() {
        // What do you need to do to initialize when constructing the boot program?
    }

    public static XrpcBootstrap getInstance() {
      return xrpcBootstrap;
    }

    /**
     * Define the name of the application
     * @return this instance
     */
    public XrpcBootstrap application(String appName) {
        return null;
    }

    /**
     * Configure the registration center
     * @param registryConfig
     * @return this instance
     */
    public XrpcBootstrap registry(RegistryConfig registryConfig) {
        return this;
    }

    /**
     * Configure serialization protocol
     * @param protocolConfig Protocol encapsulation
     * @return this instance
     */
    public XrpcBootstrap protocol(ProtocolConfig protocolConfig) {
        return this;
    }

    /**
     * -------------------The apis of the service provider-------------------
     */

    /**
     * Publishing service, register the implementation of the interface to the service center
     * @param service Service encapsulation
     * @return this instance
     */
    public XrpcBootstrap publish(ServiceConfig<GreetingsService> service) {
        return this;
    }

    /**
     * Publishing in batches
     * @param service List of service encapsulation
     * @return
     */
    public XrpcBootstrap publish(List<ServiceConfig> service) {
        return this;
    }


    /**
     * start Netty service
     */
    public void start() {

    }

    /**
     * -------------------The apis of the Service caller-------------------
     */

    public XrpcBootstrap reference(ReferenceConfig<?> reference) {
        // 在这个方法力我们是否可以拿到相关的配置项-注册中心
        return this;
    }
}
