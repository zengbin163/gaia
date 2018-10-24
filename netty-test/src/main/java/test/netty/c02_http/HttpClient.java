package test.netty.c02_http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

/**
 * Created by 张少昆 on 2018/5/26.
 */
public class HttpClient {
    public static void main(String[] args){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                        ch.pipeline().addLast(new HttpRequestEncoder());
                        ch.pipeline().addLast(new HttpClientHandler());
                    }
                });

        try{
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            eventExecutors.shutdownGracefully();
        }
    }

}

class HttpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        // System.out.println("channelActive");
        // ctx.writeAndFlush("hello there - from client11 @" + LocalDateTime.now());
        // System.out.println("---------");
        //
        // ByteBuf buffer = ctx.alloc().buffer();
        // ByteBuf byteBuf = buffer.writeBytes(("hello there - from client11 @" + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
        // ctx.writeAndFlush(byteBuf);

        ByteBuf buf = ctx.alloc().buffer();
        ByteBuf byteBuf = buf.writeBytes("a=1".getBytes(StandardCharsets.UTF_8));
        System.out.println(buf == byteBuf);

        // DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "localhost:8080/hello", byteBuf);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, "localhost:8080/hello", byteBuf);
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        // System.out.println("channelRead");
        // ctx.writeAndFlush("hello there - from client @" + LocalDateTime.now());

        System.out.println("收到的消息：" + msg);

        // if(msg instanceof HttpRequest){
        //     HttpRequest request = (HttpRequest) msg;
        //     System.out.println("messageType:" + request.headers().get("messageType"));
        //     System.out.println("businessType:" + request.headers().get("businessType"));
        //     if(HttpUtil.isContentLengthSet(request)){
        //
        //     }
        // }
        // if(msg instanceof HttpContent){
        //     HttpContent httpContent = (HttpContent) msg;
        //     ByteBuf content = httpContent.content();
        //     reader.reading(content);
        //     content.release();
        //     if(reader.isEnd()){
        //         String resultStr = new String(reader.readFull());
        //         System.out.println("Client said:" + resultStr);
        //         FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
        //                                                                 HttpResponseStatus.OK,
        //                                                                 Unpooled.wrappedBuffer("I am ok".getBytes()));
        //         response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        //         response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        //         response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        //         ctx.write(response);
        //         ctx.flush();
        //     }
        // }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelReadComplete");
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
