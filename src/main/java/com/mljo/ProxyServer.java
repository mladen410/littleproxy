package com.mljo;


import jakarta.annotation.PostConstruct;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyServer {

    @Value("${proxy.port}")
    private int port;

    private HttpProxyServer proxyServer;

    @PostConstruct
    public void startProxy() {
        proxyServer = DefaultHttpProxyServer.bootstrap()
                .withPort(port)
                .withAllowLocalOnly(false)
                .start();

        System.out.println("Forward Proxy läuft auf Port: " + port + " (HTTP + HTTPS)");
    }
}