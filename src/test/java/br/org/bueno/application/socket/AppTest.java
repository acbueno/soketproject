package br.org.bueno.application.socket;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.persistence.Temporal;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import br.org.bueno.application.socket.entity.User;
import br.org.bueno.application.socket.util.HibernateUtil;
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

    @Test
    public void saveUser() {
    	User user =  new User("Rambo", 25, 100, 20, 10);
    	HibernateUtil.saveUser(user);
    	HibernateUtil.isSaveOK();
    }

}
