package br.org.bueno.application.socket.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {


	public void messageReceived(IoSession session, Object message) {
		String str = message.toString();
		System.out.println("Message received: " + str.toString());
	}

}
