package br.org.bueno.application.socket.server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {

	private static final int PORT = 5050;

	public static void main(String[] args) {

		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger1", new LoggingFilter());
		acceptor.getFilterChain().addLast("code1", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8") )));
		acceptor.setHandler(new ServerHandler());
		try {
			acceptor.bind(new InetSocketAddress(PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
