package test.netty.tour;

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
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.Date;
import java.util.List;

/**
 * In TCP/IP, Netty reads the data sent from a peer into a ByteBuf.
 * <p>
 * Created by 张少昆 on 2018/5/14.
 */
public class TimeClient {
    int port;

    public TimeClient(int port){
        this.port = port;
    }

    public static void main(String[] args){
        int port = 8080;
        new TimeClient(port).run();
    }

    public void run(){
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception{
                        // ch.pipeline().addLast(new TimeClientHandler()); //TODO 原始版
                        // ch.pipeline().addLast(new AdvancedTimeClientHandler()); //TODO 改进版
                        // ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler()); //最佳版
                        ch.pipeline().addLast(new AdvancedTimeDecoder(), new TimeClientHandler()); //最佳版2
                    }
                });

        //We should call the connect() method instead of the bind() method.
        ChannelFuture channelFuture = bootstrap.connect("localhost", port);

        try{
            channelFuture.channel().closeFuture().sync();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            group.shutdownGracefully();
        }
    }
}

/**
 * 最简陋的版本
 */
class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;
        try{
            //            String str = buf.toString(CharsetUtil.US_ASCII); // FIXME ERROR

            //            int i = buf.readInt(); // ERROR
            long i = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println("收到的内容：" + new Date(i));
        } finally{
            buf.release();
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("读毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

    //
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time client handler added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time client handler removed");
    }

}

/**
 * 优化版本：仅适用于定长的情况。
 */
class AdvancedTimeClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    // the handler must check if buf has enough data, 4 bytes in this example,
    // and proceed to the actual business logic.
    // Otherwise, Netty will call the channelRead() method again when more data arrives,
    // and eventually all 4 bytes will be cumulated.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf in = (ByteBuf) msg;
        buf.writeBytes(in);
        in.release();

        //一次只处理4byte，多的留给下一次处理
        if(buf.readableBytes() > 4){
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("读毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

    //
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time client handler added");
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("time client handler removed");
        buf.release();
        buf = null;
    }
}

class TimeDecoder extends ByteToMessageDecoder { //ByteToMessageDecoder 是 ChannelInboundHandler的实现。read的时候自动调用decode

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        System.out.println("解码中");
        if(in.readableBytes() < 4){ //TODO 如果>4，会反复执行decode！也就是不断追加到out中！
            return;
        }
        out.add(in.readBytes(4)); //读取4byte，交给下一个handler
        System.out.println("解码成功");
    }
}

class AdvancedTimeDecoder extends ReplayingDecoder<Void> { //泛型是状态？无状态就是Void

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        System.out.println("重复解码中..");
        out.add(in.readBytes(4));
    }
}
