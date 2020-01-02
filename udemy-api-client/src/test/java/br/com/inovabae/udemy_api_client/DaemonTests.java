package br.com.inovabae.udemy_api_client;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class DaemonTests {
	public static void main(String[] args) {
		Locale currentLocale = new Locale("pt");
		Double currencyAmount = new Double(9876543.21);
		Currency currentCurrency = Currency.getInstance("BRL");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		System.out.println(currentCurrency.getSymbol());

		System.out.println("\n" + currentLocale.getDisplayName() + ", " + currentCurrency.getDisplayName() + ": "
				+ currencyFormatter.format(currencyAmount));
	}

}
