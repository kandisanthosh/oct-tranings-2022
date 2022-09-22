package com.javainuse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class ConsumerControllerClient {
	
	@Autowired
	private RetryService retryService;

	public ResponseEntity<String> getEmployee() throws Exception {
		ResponseEntity<String> response=	retryService.fetchCustomerDetails();
			
		return response;

	}

	
}