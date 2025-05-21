package com.mljo.log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.net.InetSocketAddress;

public class LoggingHttpFilters extends HttpFiltersAdapter {

    private final ChannelHandlerContext ctx;

    public LoggingHttpFilters(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        super(originalRequest, ctx);
        this.ctx = ctx;
    }

    private String getClientIp() {
        if (ctx != null) {
            InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            return remoteAddress.getAddress().getHostAddress();
        }
        return "unbekannt";
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest request) {
            String clientIp = getClientIp();
            System.out.println("[Proxy] Anfrage von " + clientIp + ": " + request.getMethod() + " " + request.getUri());
        }
        return null; // Weiterleiten wie gewohnt
    }


    @Override
    public HttpObject serverToProxyResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) httpObject;
            System.out.println("[Proxy] Antwort vom Zielserver: " + response.getStatus());
        }
        return httpObject;
    }
}