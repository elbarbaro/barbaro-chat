package com.barbaro.barbarochat.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.barbaro.barbarochat.model.ChatRoomUser;
import com.barbaro.barbarochat.service.ChatRoomService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebSocketEvents {

	private ChatRoomService chatRoomService;
	
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = 
				SimpMessageHeaderAccessor.wrap(event.getMessage());
		String chatRoomUsername = headers.getNativeHeader("chatroomuser").get(0);
		//String userId = event.getUser().getName();
		
		ChatRoomUser chatRoomUser = new ChatRoomUser(chatRoomUsername);
		chatRoomService.joinChatRoom(chatRoomUser);
	}
	
	//@EventListener
	private void handleSessionDisconnected(SessionDisconnectEvent event) {
		
		SimpMessageHeaderAccessor headers = 
				SimpMessageHeaderAccessor.wrap(event.getMessage());
		String chatRoomUsername = headers.getNativeHeader("chatroomsuser").get(0);
		ChatRoomUser chatRoomUser = new ChatRoomUser(chatRoomUsername);
		chatRoomService.leaveChatRoom(chatRoomUser);
	}
}
