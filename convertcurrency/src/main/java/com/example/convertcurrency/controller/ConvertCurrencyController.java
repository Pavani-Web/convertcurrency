package com.example.convertcurrency.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.convertcurrency.client.CurrencyFactorClient;


@RestController
@RequestMapping("/convert")
public class ConvertCurrencyController {
	
	Logger log = LoggerFactory.getLogger(ConvertCurrencyController.class);
	
	@Autowired
	private CurrencyFactorClient currencyFactorClient;
	
	@Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
	
	@GetMapping("/currency/{code}/{amount}")
	public Double getConversionFactor(@PathVariable("code") String code, @PathVariable("amount") String amount) {
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
	    
	    
	    return (circuitBreaker).run(() -> getConvertedAmount(code, amount), 
	      throwable -> getDefaultConversionFactor());
	}


	public Double getDefaultConversionFactor() {
		return 0.0;
	}
	
	public Double getConvertedAmount(String code, String amount) {
		  Double conversionFactor = currencyFactorClient.getConversionFactor(code);
		  Double amounttoConverted = Double.parseDouble(amount); 
		  Double convertedAmount= amounttoConverted * conversionFactor; 
		  return convertedAmount;
	}		
	
}
