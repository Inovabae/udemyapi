package br.com.inovabae.udemy_api_client;

import java.math.BigDecimal;
import java.util.Currency;

public class ValorMonetario {
	private Currency moeda;
	private BigDecimal currencyAmount;
	
	public ValorMonetario(Currency moeda, BigDecimal currencyAmount) {
		this.moeda = moeda;
		this.currencyAmount = currencyAmount;
	}
	
	public Currency getMoeda() {
		return moeda;
	}
	public void setMoeda(Currency moeda) {
		this.moeda = moeda;
	}
	public BigDecimal getCurrencyAmount() {
		return currencyAmount;
	}
	public void setCurrencyAmount(BigDecimal currencyAmount) {
		this.currencyAmount = currencyAmount;
	}
}
