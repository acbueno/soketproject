package br.org.bueno.application.socket.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.bind.DatatypeConverter;

public class ToolUtils {

	private static final ToolUtils INSTANCE = new ToolUtils();
	public static Logger logger = Logger.getLogger("clientLog");


	private ToolUtils() {

	}

	public static ToolUtils getInstance() {
		return INSTANCE;
	}

	public static String toHexadecimal(String text) throws UnsupportedEncodingException {
		byte[] myBytes = text.getBytes("UTF-8");

		return DatatypeConverter.printHexBinary(myBytes);
	}

	public static String hexToString(String hexStr) {
	    StringBuilder output = new StringBuilder("");

	    for (int i = 0; i < hexStr.length(); i += 2) {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }

	    return output.toString();
	}

	public static void createLogSession(String nameOfLog) throws IOException {
		FileHandler clientFileLog;
		System.getProperty("user.dir");

		File directory = new File("logs");
		if (!directory.exists()) {
			directory.mkdir();
		}

		clientFileLog = new FileHandler(directory + "/" + nameOfLog);
		logger.addHandler(clientFileLog);
		SimpleFormatter formatter = new SimpleFormatter();
		clientFileLog.setFormatter(formatter);
	}

}
