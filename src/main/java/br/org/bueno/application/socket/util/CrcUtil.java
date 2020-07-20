package br.org.bueno.application.socket.util;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class CrcUtil {

	private final static CrcUtil INSTANCE = new CrcUtil();

	private CrcUtil() {

	}

	public static final CrcUtil getInstance() {
		return INSTANCE;
	}

	public void calculateCRC8(String data) {

		byte bytes[] = data.getBytes();

		Checksum checksum = new CRC32();

		checksum.update(bytes, 0, bytes.length);

		long checksumValue = checksum.getValue();

		System.out.println("CRC32 checksum for input string is.  " + checksumValue);
	}

}
