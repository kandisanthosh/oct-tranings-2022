package com.javainuse.controllers;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RetryService {
	
    @Retryable(value = {RetryException.class}, maxAttempts = 4, exclude = RetryException.class, label = "retry API", backoff = @Backoff(delay = 5000))
    public ResponseEntity<String> RetryTestExclude() throws Exception {
        System.out.println("Retrying Attempt...");
        
        String baseUrl = "http://localhost:8081/employee";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try{
		response=restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(),String.class);
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
        
        //if retry service is down throw some exception
        if (response == null) { 
            throw new RetryException("Failed retry Again....");
        }
		return response;

    }

    @Recover
    public void connectionException(RetryException e) {
        System.out.println("Retry failure");
    }

	public ResponseEntity<String> fetchCustomerDetails() throws Exception {
	
		ResponseEntity<String> response=RetryTestExclude();
		
		System.out.println(response.getBody());
		return response;
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}
