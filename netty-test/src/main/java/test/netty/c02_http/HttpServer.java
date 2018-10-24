package test.netty.c02_http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * Created by zengbin on 2018/5/26.
 */
public class HttpServer {
    public static void main(String[] args){
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup workers = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, workers)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        System.out.println("---socketChannel: " + ch);
                        // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                        ch.pipeline().addLast(new HttpResponseEncoder());
                        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                        ch.pipeline().addLast(new HttpRequestDecoder());
                        ch.pipeline().addLast(new HttpServerHandler2());
                        ch.pipeline().addLast(new HttpServerHandler());
                    }

                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try{
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8080).sync();
            // System.out.println("channelFuture.isVoid(): " + channelFuture.isVoid());
            // System.out.println("channelFuture.isDone(): " + channelFuture.isDone());
            // System.out.println("channelFuture.isCancelled(): " + channelFuture.isCancelled());
            // System.out.println("channelFuture.isCancellable(): " + channelFuture.isCancellable());
            // System.out.println("channelFuture.isSuccess(): " + channelFuture.isSuccess());
            //
            // System.out.println("channelFuture == channelFuture.channel().closeFuture(): " + (channelFuture == channelFuture.channel().closeFuture()));

            channelFuture.channel().closeFuture().sync();

        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }

    }
}

//还是Inbound
class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("---HttpServerHandler.channelActive");
        System.out.println("---HttpServerHandler.ChannelHandlerContext: " + ctx.hashCode());
        System.out.println("---HttpServerHandler.ChannelHandlerContext.handler: " + ctx.handler());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        // // super.channelRead(ctx, msg); //TODO 必须注释？？ 调用下一个，必须吗？奇怪
        // System.out.println("channelRead");
        // ByteBuf byteBuf = (ByteBuf) msg;
        // System.out.println("收到的消息: [" + byteBuf.toString(StandardCharsets.UTF_8) + "]");
        // byteBuf.release();
        // // System.out.println("收到的消息: [" + msg + "]");

        System.out.println("---HttpServerHandler.channelRead");
        System.out.println("---HttpServerHandler.ChannelHandlerContext: " + ctx.hashCode());
        System.out.println("---HttpServerHandler.ChannelHandlerContext.channel: " + ctx.channel().id());
        System.out.println("---HttpServerHandler.ChannelHandlerContext.handler: " + ctx.handler());

        System.out.println("-------收到的消息：" + msg); //TODO 注意，拆分了HTTP_REQUEST和CONTENT，所以这里会进入两次
        if(msg instanceof HttpRequest){
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            System.out.println("request.protocolVersion: " + request.protocolVersion().text());
            System.out.println("request.method: " + request.method());
            System.out.println("request.uri: " + request.uri());
            System.out.println("request.headers: " + request.headers());
        }
        //else //TODO 是否需要else，就要看是否使用了HttpObjectAggregator
        if(msg instanceof HttpContent){
            if(msg instanceof LastHttpContent){
                System.out.println("已到content末尾");
            } else{
                HttpContent content = (HttpContent) msg;
                System.out.println("收到的content：" + content.content().toString(StandardCharsets.UTF_8));
            }
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("it works".getBytes()));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelReadComplete"); //嗯嗯嗯？？需不需要调用 super.channelReadComplete(ctx)？？？感觉没啥必要啊
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        System.out.println("userEventTriggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelWritabilityChanged");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        // ctx.channel().close();
        ctx.close();//TODO 不需要吧？
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("handlerRemoved");
    }


}

/**
 * 仅仅为了看一下ctx和ctx.handler，和上面的是不是同一个
 */
class HttpServerHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
        System.out.println("HttpServerHandler2.channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("---HttpServerHandler2.channelActive");
        System.out.println("---HttpServerHandler2.ChannelHandlerContext: " + ctx.hashCode());
        System.out.println("---HttpServerHandler2.ChannelHandlerContext.channel: " + ctx.channel().id());
        System.out.println("---HttpServerHandler2.ChannelHandlerContext.handler: " + ctx.handler());
        super.channelActive(ctx); //TODO 必须加上这一句，才会调用pipeline中的下一个handler的channelActive(ctx)方法！否则不会调用！
        //本质上，就是 fireChannelActive
    }
}