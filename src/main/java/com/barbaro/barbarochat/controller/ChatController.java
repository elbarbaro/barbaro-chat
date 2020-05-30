package com.barbaro.barbarochat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.barbaro.barbarochat.model.ChatRoomUser;
import com.barbaro.barbarochat.model.Message;
import com.barbaro.barbarochat.service.ChatRoomService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatController {
	
	private ChatRoomService chatRoomService;

	@MessageMapping("/publish") /* Endpoint - client */
	@SendTo("/topic/chatbarbaro")
	public Message publishMessage(Message message) {
		return message;
	}
	
	@MessageMapping("/disconnect")
	public void actionDisconnect(ChatRoomUser user) {
		chatRoomService.leaveChatRoom(user);
	}
	
	@SubscribeMapping("/connected.users")
	public List<ChatRoomUser> listConnectedUsersOnSubscribe() {
		return chatRoomService.findAllChatRoomUsers();
	}
}
