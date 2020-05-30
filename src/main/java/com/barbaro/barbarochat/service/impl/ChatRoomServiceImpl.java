package com.barbaro.barbarochat.service.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.barbaro.barbarochat.model.ChatRoomUser;
import com.barbaro.barbarochat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

	private final SimpMessagingTemplate webSocketMessagingTemplate;
	
	private ChatRoomDatabase chatRoomDatabase = new ChatRoomDatabase();
	
	@Override
	public void joinChatRoom(ChatRoomUser user) {
		chatRoomDatabase.addChatRoomUser(user);
		notifylistConnectedChatRoomUsers();
	}

	@Override
	public void leaveChatRoom(ChatRoomUser user) {
		chatRoomDatabase.removeChatRoomUser(user);
		notifylistConnectedChatRoomUsers();
	}

	@Override
	public void notifylistConnectedChatRoomUsers() {
		webSocketMessagingTemplate.convertAndSend("/connected.users", findAllChatRoomUsers());
	}

	@Override
	public List<ChatRoomUser> findAllChatRoomUsers() {
		return chatRoomDatabase.getListConnectedUsers();
	}

	private static class ChatRoomDatabase {
		
		private List<ChatRoomUser> listConnectedUsers = new CopyOnWriteArrayList<>();
		
		private void addChatRoomUser(ChatRoomUser user) {
			listConnectedUsers.add(user);
		}
		
		private void removeChatRoomUser(ChatRoomUser user) {
			listConnectedUsers.removeIf(
					u -> u.getUsername().equals(user.getUsername()));
		}
		
		private List<ChatRoomUser> getListConnectedUsers() {
			return listConnectedUsers;
		}
	}
}
