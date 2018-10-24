package test.jdk.socket;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 张少昆 on 2017/11/4 0004.
 */
public class SocketTest {

	@Test
	public void server() throws IOException{
		ServerSocket serverSocket = new ServerSocket(8080);

		Socket socket = serverSocket.accept();
		InputStream in = socket.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(in);

		byte[] bytes = new byte[1024 * 1024];

		int len = bis.read(bytes);

		System.out.println(new String(bytes));
		OutputStream out = socket.getOutputStream();
		OutputStreamWriter writer=new OutputStreamWriter(out);

		writer.write("");
	}
}
