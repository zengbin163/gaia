package test.netty.tour.advanced;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import test.netty.tour.advanced.pojo.UnixTime;

import java.util.List;

/**
 * Created by 张少昆 on 2018/5/14.
 */
public class TimeClient {
    static int port = 8080;

    public static void main(String[] args){
        run();
    }

    public static void run(){
        EventLoopGroup oneGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(oneGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());//TODO 注意，是channelInbound
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);
        try{
            ChannelFuture channelFuture = bootstrap.connect("localhost", port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            oneGroup.shutdownGracefully();
        }

    }
}

//接收响应用的
class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        UnixTime time = (UnixTime) msg;
        System.out.println("收到了：" + msg);
        ctx.close();//TODO 需要关闭ctx
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("读完");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("TimeClientHandler added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("TimeClientHandler removed");
    }
}

class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        System.out.println("准备解码");
        if(in.readableBytes() < 4){
            System.out.println("不足以解码");
            return;
        }
        long ui = in.readUnsignedInt();
        System.out.println("解码完成");
        out.add(new UnixTime(ui));
    }
}