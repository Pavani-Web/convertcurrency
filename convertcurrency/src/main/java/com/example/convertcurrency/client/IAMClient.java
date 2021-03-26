package com.example.convertcurrency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.convertcurrency.model.User;
import com.fasterxml.jackson.databind.JsonNode;

@FeignClient(name = "IAM-LOGIN", url = "https://us-south.appid.cloud.ibm.com/")
public interface IAMClient {

@RequestMapping(value = "/oauth/v4/c3844630-7a07-46b2-aa1f-1fda5651b637/token", produces = { "*/*" }, method = RequestMethod.POST)
	
	ResponseEntity<JsonNode> getToken(
			@RequestHeader("Authorization") String authorizationHeader,@RequestBody User user);
}