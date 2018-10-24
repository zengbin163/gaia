package test.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * Created by 张少昆 on 2017/12/6 0006.
 */
public class MinaService {
	public static void main(String[] args){
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter()); // add log filter
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));//接收对象
		acceptor.setHandler(new DemoHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		try{
			acceptor.bind(new InetSocketAddress(9123));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 负责session对象的创建监听以及消息发送和接收的监听
	 */
	private static class DemoHandler extends IoHandlerAdapter {
		@Override
		public void sessionCreated(IoSession session) throws Exception{
			super.sessionCreated(session);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception{
			super.sessionOpened(session);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception{
			super.sessionClosed(session);
		}

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception{
			super.messageReceived(session, message);
			String str = message.toString();
			Date date = new Date();
			session.write(date.toString());
			System.out.println("接收到的数据是：" + str);
		}

		@Override
		public void messageSent(IoSession session, Object message) throws Exception{
			super.messageSent(session, message);
		}
	}
}
