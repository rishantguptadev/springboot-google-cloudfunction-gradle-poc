package com.demo.utility;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UtilityService {
	
	private ObjectMapper objectMapper;
	
	public UtilityService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	public void process(PubSubMessage message) {
	        String decoded = new String(Base64.getDecoder().decode(message.getData()));
	        System.out.println("📩 Raw PubSub data: " + message.getData());
	        System.out.println("✅ Decoded message: " + decoded);
	        try {
	        	System.out.println("✅ objectMapper: " + objectMapper.writeValueAsString("adsjg;al;KET;LERHB"));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
    }
}
