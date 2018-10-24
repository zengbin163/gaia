package test.netty.c00;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * Created by zengbin on 2018/5/11.
 */
class MyChannelHandlerList {
    //TODO 不明白的是，这里只有被添加/删除和异常，那神马时候调用业务逻辑？？
    static class ChA implements ChannelHandler {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChA handlerAdded");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChA handlerRemoved");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            System.out.println("ChA exceptionCaught");
        }
    }

    // 嗯？
    static class ChB implements ChannelInboundHandler {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelRegistered");
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelUnregistered");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelActive");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelInactive");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
            System.out.println("ChB channelRead：" + msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelReadComplete");
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
            System.out.println("ChB userEventTriggered：" + evt);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB channelWritabilityChanged");
        }

        //----------以下来自ChannelHandler
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB handlerAdded");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChB handlerRemoved");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            System.out.println("ChB exceptionCaught");
        }
    }

    static class ChC implements ChannelOutboundHandler{

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception{
            System.out.println("ChC bind");
        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception{
            System.out.println("ChC connect");
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception{
            System.out.println("ChC disconnect");
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception{
            System.out.println("ChC close");
        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception{
            System.out.println("ChC deregister");
        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChC read");
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception{
            System.out.println("ChC write");
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChC flush");
        }

        //-----------以下来自ChannelHandler
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChC handlerAdded");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
            System.out.println("ChC handlerRemoved");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            System.out.println("ChC exceptionCaught");
        }
    }

    //适配器，其实还新加了一个方法 isSharable()
    static class ChD extends ChannelHandlerAdapter{
        //。。。
    }

}
