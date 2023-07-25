package com.oliver;

import com.oliver.impl.HelloXrpcImpl;

/**
 * @author xiaorui
 */
public class Application {
    public static void main(String[] args) {
        // Service providers, help register services and start services.
        // 1. Package the services to be released
        ServiceConfig<HelloXrpc> service = new ServiceConfig<>();
        service.setInterface(HelloXrpc.class);
        service.setRef(new HelloXrpcImpl());

        // 2. Define the registration center

        // 3. Start the service provider by starting the boot program
        // （1）Configuration -- Application Name -- Registry -- Serialization Protocol -- Compression Method
        // （2）Publishing service
        XrpcBootstrap.getInstance()
                .application("first-xrpc-provider")
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2182"))
                .protocol("jdk")
                // publishing service
                .publish(service)
                // start service
                .start();

    }
}
