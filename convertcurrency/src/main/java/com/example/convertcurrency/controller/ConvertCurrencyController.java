package com.example.convertcurrency.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.convertcurrency.client.CurrencyFactorClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/convert")
public class ConvertCurrencyController {
	
	Logger log = LoggerFactory.getLogger(ConvertCurrencyController.class);
	
	@Autowired
	private CurrencyFactorClient currencyFactorClient;
	
	@GetMapping("/currency/{code}/{amount}")
	@CircuitBreaker(name="conversionFactorService", fallbackMethod = "conversionFactorFallback")
	public Double getConversionFactor(@PathVariable("code") String code, @PathVariable("amount") String amount) {
		log.info("Entered ConvertCurrencyController::getConversionFactor");		
		  Double conversionFactor = currencyFactorClient.getConversionFactor(code);
		  Double amounttoConverted = Double.parseDouble(amount); 
		  Double convertedAmount= amounttoConverted * conversionFactor; 
		  return convertedAmount;
	}


	public Double conversionFactorFallback(String code, String amount, Throwable e){
        return 0.0;

    }
	
}
