package com.oliver;

/**
 * @author xiaorui
 */
public class Application {
    public static void main(String[] args) {
        // Do everything possible to get the agent object
        // Use ReferenceConfig to encapsulate
        // reference must have a template method to generate agents, get()
        ReferenceConfig<HelloXrpc> reference = new ReferenceConfig();
        reference.setInterface(HelloXrpc.class);

        // What did the agent do?
        // 1. Connect to the registration center
        // 2. Pull to the service list
        // 3. Select a service and supervise the connection
        // 4. Send the request and carry some information (interface name, parameter list, method name)
        XrpcBootstrap.getInstance()
                .application("first-xrpc-consumer")
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(reference);

        // Get the proxy object
        HelloXrpc helloXrpc = xxx.get();
        helloXrpc.sayHi("message");
    }
}
