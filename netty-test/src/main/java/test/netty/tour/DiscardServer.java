package test.netty.tour;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.time.LocalDateTime;

/**
 * 扔掉所有接收到的消息，且不作响应！
 * <p>
 * Created by 张少昆 on 2018/5/14.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port){
        this.port = port;
    }

    public static void main(String[] args){
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        } else{
            port = 8080;
        }
        new DiscardServer(port).run();
    }

    public void run(){
        // 默认的线程数 MultithreadEventLoopGroup.DEFAULT_EVENT_LOOP_THREADS =
        // Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //有很多实现！  bossGroup用于 接受链接
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //workerGroup用于 处理链接，并将链接注册到worker

        ServerBootstrap serverBootstrap = new ServerBootstrap(); //可以轻松地启动ServerChannel
        serverBootstrap
                .group(bossGroup, workerGroup) //Set the EventLoopGroup for the parent (acceptor) and the child (client)
                .channel(NioServerSocketChannel.class) //用这个，或者用channelFactory。用于创建Channel对象
                .childHandler(new ChannelInitializer<SocketChannel>() { //这个，处理Channel的请求？？？
                    // 当Channel被注册的时候，会调用该方法。
                    // 但是，当该方法返回的时候，该实例（谁）会被从Channel的ChannelPipeline中移除！
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        ch.pipeline().addLast(new DiscardServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true); //after the acceptor accepted the Channel

        try{
            System.out.println("开始绑定");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("绑定完成，设置未来关闭");
            channelFuture.channel().closeFuture().sync();
            System.out.println("关闭完成");
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    //This method is called with the received message, whenever new data is received from a client.
    //Please keep in mind that it is the handler's responsibility to release any reference-counted object passed to the handler.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        System.out.println("收到了消息 @" + LocalDateTime.now());
        ((ByteBuf) msg).release();

        //因为ByteBuf是引用计数对象，所以必须手动释放，正常情况下是这样的：
//        try {
//            // Do something with msg
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }

//        ByteBuf buf = (ByteBuf) msg;
//        try {
////            while (buf.isReadable()) { // (1)
////                System.out.print((char) buf.readByte());
////                System.out.flush();
////            }
//
//            //这句话等效于上面的循环，且更高效
//            System.out.println(buf.toString(CharsetUtil.US_ASCII));
//        } finally {
//            ReferenceCountUtil.release(msg); // (2) 或者 buf.release()。
//        }
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

    // 验证initChannel对handler的调用问题。
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("discard server handler added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("discard server handler removed");
    }
}