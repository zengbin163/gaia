package test.netty.c01.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import test.netty.c01.common.OutHandler;
import test.netty.c01.client.DemoClientHandler;

/**
 * 服务器Channel通道初始化设置
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        ChannelPipeline pipeline = socketChannel.pipeline();
        //字符串解码和编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        //服务器的逻辑
        //添加一个Hanlder用来处理各种Channel状态
        pipeline.addLast("handlerIn", new DemoClientHandler());
        //添加一个Handler用来接收监听IO操作的
        pipeline.addLast("handlerOut", new OutHandler()); //公共处理类说明，可以不写

    }

}