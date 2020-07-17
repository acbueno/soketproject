package br.org.bueno.application.socket.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import br.org.bueno.application.socket.util.ToolUtils;

public class Client {

	private static int TIMEOUT = 1000;
	private static String HOSTNAME = "127.0.0.1";
	private static int PORT = 5050;
	private static ToolUtils toolUtils = ToolUtils.getInstance();

	public static void main(String[] args) throws Throwable {

		NioSocketConnector connector = new NioSocketConnector();

		configureConnector(connector);

		IoSession session = connect(connector);

		if (session != null) {
			sendCommands(session);
		}
		close(connector, session);

	}

	public static void configureConnector(final NioSocketConnector connector) {

		connector.setConnectTimeoutMillis(TIMEOUT);
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(new ClientHandler());

	}

	private static IoSession connect(final NioSocketConnector connector) throws InterruptedException {

		IoSession session = null;

		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
			future.awaitUninterruptibly();
			session = future.getSession();

		} catch (RuntimeException e) {
			System.err.println("Falied connection.");
			e.printStackTrace();
		}
		return session;
	}

	private static void sendCommands(final IoSession session) throws SecurityException, IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			String text;
			toolUtils.createLogSession("client_log.txt");

			do {
				System.out.println("Preencha o nome, idade, altura e peso separado por virgula. Ex : Rambo, 40, 182, 85"
						+ "\nPara enviar os dados ao termino pressione enter e para sair digite quit");
				text = scanner.nextLine();
				session.write(text);
				toolUtils.logger.info("Send message server: " + text);

			} while (!"quit".equalsIgnoreCase(text));
			session.close();
		}

	}



	private static void close(final NioSocketConnector connector, final IoSession session) {

		if (session != null) {
			if (session.isConnected()) {
				session.close();
				session.getCloseFuture().awaitUninterruptibly();
			}
		}
		connector.dispose();

	}

}
