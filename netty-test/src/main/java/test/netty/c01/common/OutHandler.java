package test.netty.c01.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author linge
 * @ClassName: OutHandler
 * @Description: 公共处理类
 * @date 2018年1月8日 下午3:48:40
 */
public class OutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void connect(ChannelHandlerContext ctx,
                        SocketAddress remoteAddress,
                        SocketAddress localAddress,
                        ChannelPromise promise) throws Exception{
        super.connect(ctx, remoteAddress, localAddress, promise);
        System.out.println("<<<<<<<<<<<<<<< connect server success >>>>>>>>>>>>>>>>");
    }

    @Override
    public void bind(ChannelHandlerContext ctx,
                     SocketAddress localAddress,
                     ChannelPromise promise) throws Exception{
        super.bind(ctx, localAddress, promise);
        System.out.println("<<<<<<<<<<<<<<< server bind success >>>>>>>>>>>>>>>>");
    }
}