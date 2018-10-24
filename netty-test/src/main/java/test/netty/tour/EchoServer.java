package test.netty.tour;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

/**
 * Created by zengbin on 2018/5/14.
 */
public class EchoServer {
    int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args){
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        } else{
            port = 8080;
        }
        new EchoServer(port).run();
    }

    public void run(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        serverBootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try{
            System.out.println("开始绑定");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("绑定完成，设置未来关闭");
            channelFuture.channel().closeFuture().sync();
            System.out.println("关闭完成");
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}

class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;

        String str = buf.toString(CharsetUtil.US_ASCII); //TODO 改方法不会修改 buffer的 readerIndex 或者 writerIndex。

        System.out.println(str);
//            ctx.write(str);
//            ctx.flush();
        ctx.write(msg); // TODO 该方法不会flush，必须手动flush
        ctx.flush();
        // TODO 不需要手动释放了，因为：Netty releases it for you when it is written out to the wire.

//        也可以 ctx.writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("读完了 @" + LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}
