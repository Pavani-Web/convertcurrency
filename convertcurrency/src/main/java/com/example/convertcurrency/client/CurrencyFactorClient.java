package com.example.convertcurrency.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name ="CURRENCYFACTOR", url="http://localhost:8080")
public interface CurrencyFactorClient {

	@RequestMapping(value = "/currecyconversionfactor/get/{code}", produces = {"*/*"}, method = RequestMethod.GET)
	 Double getConversionFactor(@PathVariable("code") String code);
}
