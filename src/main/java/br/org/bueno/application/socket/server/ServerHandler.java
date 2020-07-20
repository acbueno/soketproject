package br.org.bueno.application.socket.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.org.bueno.application.socket.entity.User;
import br.org.bueno.application.socket.util.Crc8Util;
import br.org.bueno.application.socket.util.CrcUtil;
import br.org.bueno.application.socket.util.HibernateUtil;
import br.org.bueno.application.socket.util.ToolUtils;

public class ServerHandler extends IoHandlerAdapter {

	private ToolUtils toolUtils = ToolUtils.getInstance();
	Crc8Util crc8Util = Crc8Util.getInstance();

	public void messageReceived(IoSession session, Object message) throws IOException {
		String str = message.toString();

		System.out.println("Message received Server " + str);
		crc8Util.reset();
		crc8Util.update(str.getBytes());
		toolUtils.logger.info("CRC8 is " +  crc8Util.getValue());
		if (saveData(str)) {
			Date date = new Date();
			String dateToHex = ToolUtils.toHexadecimal(date.toString());
			session.write(toolUtils.hexToString(dateToHex));
			ToolUtils.createLogSession("server_log.txt");
			toolUtils.logger.info("Server received message Server " + dateToHex);

		}

		System.out.println("Message written ...");
		session.close();
	}

	private boolean saveData(String str) throws UnsupportedEncodingException {
		String[] messgeSplit = str.split(",");
		String name = toolUtils.toHexadecimal(messgeSplit[0]);
		String age = toolUtils.getInstance().toHexadecimal(messgeSplit[1]);
		String weight = toolUtils.getInstance().toHexadecimal(messgeSplit[2]);
		String height = toolUtils.getInstance().toHexadecimal(messgeSplit[3]);
		String nameOfLength = toolUtils.getInstance().toHexadecimal(String.valueOf(messgeSplit[0].length()));

		System.out.println("message values: " + messgeSplit[0] + " | " + messgeSplit[1] + " | " + messgeSplit[2] + " | "
				+ messgeSplit[0].length() + " | " + messgeSplit[3]);

		User user = new User(name, Integer.valueOf(age), Integer.valueOf(weight), Integer.valueOf(height),
				Integer.valueOf(nameOfLength));

		Transaction transaction = null;

		try (Session sessionHibernate = HibernateUtil.getSessionFactory().getCurrentSession()) {

			transaction = sessionHibernate.beginTransaction();
			sessionHibernate.save(user);

			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction == null) {
				transaction.rollback();
				System.out.println("Foi efetuado roolback");
			}
			e.printStackTrace();
			return false;
		}
	}

}
