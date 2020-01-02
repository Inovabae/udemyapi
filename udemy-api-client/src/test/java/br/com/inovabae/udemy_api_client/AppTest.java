package br.com.inovabae.udemy_api_client;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		Locale currentLocale = new Locale("pt");
		Double currencyAmount = new Double(9876543.21);
		Currency currentCurrency = Currency.getInstance(currentLocale);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

		System.out.println(currentLocale.getDisplayName() + ", " + currentCurrency.getDisplayName() + ": "
				+ currencyFormatter.format(currencyAmount));
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
}
