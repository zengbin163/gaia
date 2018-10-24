package test.netty.c01.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 客户端Channel通道初始化设置
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        //管道！添加处理流程，由后续调用
        ChannelPipeline pipeline = socketChannel.pipeline();
        //这里引用默认的编码解码，默认是UTF-8的<code>ByteBuf</code>
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        //客户端的逻辑
        pipeline.addLast("handler", new DemoClientHandler());

    }

}