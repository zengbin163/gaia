package test.netty.tour;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.time.LocalDateTime;

/**
 * Created by zengbin on 2018/5/14.
 */
public class TimeServer {
    int port;

    public TimeServer(int port){
        this.port = port;
    }

    public static void main(String[] args){
        int port = 8080;

        new TimeServer(port).run();
    }

    public void run(){
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        System.out.println(parentGroup);
        System.out.println(childGroup);

        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        System.out.println(ch.parent());
                        System.out.println(ch.eventLoop().parent());
                        ch.pipeline().addLast(new TimeServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try{
            ChannelFuture future = b.bind(port).sync();//绑定后变成同步
            Channel channel = future.channel();
//            channel.close().sync(); //pipeline.close(). Waits for this future until it is done, and rethrows the cause of the failure if this future failed.
//            channel.close().await(); //Waits for this future to be completed.
            channel.closeFuture().sync();

        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}

// 一次性的，链接创建后不读取，直接返回消息。所以不使用read，直接active即可。
class TimeServerHandler extends ChannelInboundHandlerAdapter {

    // the channelActive() method will be invoked when a connection is established and ready to generate traffic.
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        ByteBuf buffer = ctx.alloc().buffer(4);//ctx.alloc() Get the current ByteBufAllocator. why??? efficient
        //注意，没有flip()，因为ByteBuf为writer和reader分别设置了指针，互不影响。但NIO必须flip()！显然ByteBuf更爽
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // ChannelFuture 代表了尚未发生的 I/O 操作。
        // 就是说，任何请求的操作可能还没有被执行 - 因为Netty中的所有操作都是异步的。
        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);

        // 因为是异步的，所以你需要监听器来关闭ctx。下面等同于  channelFuture.addListener(ChannelFutureListener.CLOSE)
//        channelFuture.addListener((ChannelFutureListener) future -> {
//            assert future == channelFuture;
//            ctx.close();
//        });

        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        System.out.println("读取了");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("读完了 @" + LocalDateTime.now());
    }

    //io异常，或者handler抛出异常导致的。正常情况下，应该记录并关闭channel
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

    //
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time server handler added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time server handler removed");
    }
}
