package com.example.convertcurrency.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.convertcurrency.client.CurrencyFactorClient;
import com.example.convertcurrency.client.IAMClient;
import com.example.convertcurrency.model.User;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/convert")
public class ConvertCurrencyController {
	
	Logger log = LoggerFactory.getLogger(ConvertCurrencyController.class);
	
	@Autowired
	private CurrencyFactorClient currencyFactorClient;
	
	@Autowired
	private IAMClient iamClient;
	
	
	@GetMapping("/currency/{code}/{amount}")
	@CircuitBreaker(name="conversionFactorService", fallbackMethod = "conversionFactorFallback")
	public Double getConversionFactor(@PathVariable("code") String code, @PathVariable("amount") String amount) throws UnsupportedEncodingException {
		log.info("Entered ConvertCurrencyController::getConversionFactor");
		  String token =  tokenDetails();
		  String authorizationHeader = "Bearer " + token; 
		  Double conversionFactor = currencyFactorClient.getConversionFactor(authorizationHeader,code);
		  Double amounttoConverted = Double.parseDouble(amount); 
		  Double convertedAmount= amounttoConverted * conversionFactor; 
		  return convertedAmount;
	}

    /*
     * conversionFactorFallback
     * if currencyFactor services is down then it will call fallback method.
     */
	public Double conversionFactorFallback(String code, String amount, Throwable e){
		log.info("Entered ConvertCurrencyController::conversionFactorFallback method");
        return 0.0;

    }
	/*
	 * tokenDetails()
	 * Getting the token from IBM IAM serviceS
	 */
	public String tokenDetails() throws UnsupportedEncodingException {
		log.info("Entered ConvertCurrencyController::tokenDetails method for to get token");
		ResponseEntity<JsonNode> response = null;
		String accessToken = "";
		String authHeader = "Basic " + Base64.getEncoder().encodeToString(
				("995cd0b5-8a11-48cc-861b-0032888ae81f" + ":" + "OTJmOTU4YzEtZDUwYi00MjU0LWEzZGQtNjc4YmNmZTkyY2Ji")
						.getBytes("utf-8"));
		User user = new User();
		user.setGrant_type("client_credentials");

		try {
			response = iamClient.getToken(authHeader, user);
		} catch (Exception e) {
			log.error("Entered ConvertCurrencyController::exception block from IAMClient");
			e.getStackTrace();
		}
		if (response != null) {
			accessToken = response.getBody().findValue("access_token").asText();
		}

		return accessToken;
	}
}	
	
