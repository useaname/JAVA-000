package io.github.kimmking.gateway.outbound.netty4;

import io.github.kimmking.gateway.outbound.OutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NettyClientOutboundHandler implements OutboundHandler {
    private NettyHttpClient client;

    public NettyClientOutboundHandler(String proxyServer){
        this.client = new NettyHttpClient(proxyServer);
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws InterruptedException {
        client.connect(fullRequest, ctx);
    }


    @Deprecated
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            String value = NettyInboundHandler.result;
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes()));
            response.headers().add(fullRequest.headers());
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", value.length());

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

