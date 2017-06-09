package com.tinderboxhq.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/***
 * Authentication service
 *
 */
public class AuthenticationService extends TextWebSocketHandler {

	private static Logger logger = LogManager.getLogger(AuthenticationService.class);

	/**
	 * Constructor
	 */
	public AuthenticationService() {
		logger.info("<AuthenticationService> initialised");
	}
	/**
	 * After connection closed
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String prefix = "afterConnectionClosed() ";
		logger.info(prefix);
	}

	/**
	 * After connection established
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String prefix = "afterConnectionEstablished() ";
		logger.info(prefix);
	}

	/**
	 * Handle text message
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		String prefix = "handleTextMessage() ";
		JsonFactory jsonFactory = new JsonFactory();
		jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
		ObjectMapper mapper = new ObjectMapper(jsonFactory);
		Map map = null;
		try {
			map = mapper.readValue(textMessage.getPayload(), Map.class);
			logger.info(prefix + " message received:" + map.get("message"));
			
		} catch (JsonProcessingException jpexc) {
			logger.error(jpexc);
		}	
		
		session.sendMessage(new TextMessage(mapper.writeValueAsString("test")));
	}
	
	/**
	 * Handle transport error
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		String prefix = "handleTransportError() ";
		logger.info(prefix);
		session.close(CloseStatus.SERVER_ERROR);
	}
}
