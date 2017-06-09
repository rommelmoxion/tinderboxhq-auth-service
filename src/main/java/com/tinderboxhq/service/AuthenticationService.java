package com.tinderboxhq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.tinderboxhq.api.ResponseCodes;
import com.tinderboxhq.api.entity.UserEntity;
import com.tinderboxhq.api.model.Response;


/***
 * Authentication service
 *
 */
@Controller
public class AuthenticationService {

	private static Logger logger = LogManager.getLogger(AuthenticationService.class);

	/**
	 * Constructor
	 */
	public AuthenticationService() {
		logger.info("<AuthenticationService> initialised");
	}
	
	@MessageMapping("/user")
	@SendTo("/topic/messages")
	public Response authenticateUser(String email, String password, String last_token, String mfa_code) {
		String prefix = "authenticateUser() ";
		logger.info(prefix + "email:" + email + " password:***" + " last_token:" + last_token + " mfa_code:");
		Response response = new Response();
		try {
			response.setResponseCode(ResponseCodes.OK);
			UserEntity userEntity = new UserEntity();
			userEntity.setAuthenticated(true);
			userEntity.setExpires(0);
			userEntity.setMd_challenge("test");
			userEntity.setMfa_required(true);
			userEntity.setSession_data("test");
			userEntity.setToken("test");
			response.setData(userEntity);

		} catch(Exception exc) {
			logger.error(exc);
			
		}
		return response;
		
	}
	
}

