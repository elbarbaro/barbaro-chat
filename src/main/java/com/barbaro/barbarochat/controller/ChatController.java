package com.barbaro.barbarochat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.barbaro.barbarochat.model.Message;

@Controller
public class ChatController {

	@MessageMapping("/publish") /* Endpoint - client */
	@SendTo("/topic/chatbarbaro")
	public Message publishMessage(Message message) {
		return message;
	}
}
