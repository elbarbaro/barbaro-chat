package com.barbaro.barbarochat.service;

import java.util.List;

import com.barbaro.barbarochat.model.ChatRoomUser;

public interface ChatRoomService {
	
	void joinChatRoom(ChatRoomUser user);
	void leaveChatRoom(ChatRoomUser user);
	void notifylistConnectedChatRoomUsers();
	List<ChatRoomUser> findAllChatRoomUsers();
}
