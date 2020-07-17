package br.org.bueno.application.socket;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.persistence.Temporal;

import org.junit.Test;

import br.org.bueno.application.socket.util.ToolUtils;


/**
 * Unit test for simple App.
 */
public class AppTest  {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */

	private static ToolUtils toolUtils = ToolUtils.getInstance();


    @Test
	public void testStringToHext() throws UnsupportedEncodingException {
		String hexString = "72616D626F";
	    String nameToHex = toolUtils.toHexadecimal("rambo");
		assertEquals(hexString, nameToHex );
	}
    @Test
	public void textHexToString() {
		String name = "rambo";
		String hexToConvertString = toolUtils.hexToString("72616D626F");
		assertEquals(name, hexToConvertString);
	}

}
