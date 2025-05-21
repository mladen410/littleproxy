package com.mljo;


import com.mljo.log.LoggingHttpFilters;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.PostConstruct;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.netty.handler.codec.http.HttpRequest;


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
                .withFiltersSource(new HttpFiltersSourceAdapter() {
                    @Override
                    public HttpFiltersAdapter filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        return new LoggingHttpFilters(originalRequest, ctx);
                    }
                })
                .start();

        System.out.println("Forward Proxy läuft auf Port: " + port + " (HTTP + HTTPS)");
    }

    HttpFiltersSourceAdapter filtersSource = new HttpFiltersSourceAdapter() {
        @Override
        public HttpFiltersAdapter filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
            return new LoggingHttpFilters(originalRequest, ctx);
        }
    };
}