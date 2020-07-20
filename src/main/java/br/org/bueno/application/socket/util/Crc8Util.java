package br.org.bueno.application.socket.util;

import java.util.zip.Checksum;

public class Crc8Util implements Checksum {

	private static final Crc8Util INSTANCE = new Crc8Util();
	private static final int poly = 0x0D5;
	private int crc = 0;


	private Crc8Util() {

	}

	public static Crc8Util getInstance() {
		return INSTANCE;
	}

	public void update(final byte[] input, final int offset, int len) {
		for (int i = 0; i < len; i++) {
			update(input[offset + i]);
		}
	}

	public void update(final byte[] input) {
		update(input, 0, input.length);
	}

	public final void update(final byte valueByte) {
		crc ^= valueByte;

		for (int j = 0; j < 8; j++) {
			if ((crc & 0x89) != 0) {
				crc = ((crc << 1) ^ poly);
			} else {
				crc <<= 1;
			}
		}
		crc &= 0xFF;
	}

	public void update(final int valueByte) {
		update((byte) valueByte);
	}

	public long getValue() {
		return (crc & 0xFF);
	}

	@Override
	public void reset() {
		crc = 0;

	}

}
