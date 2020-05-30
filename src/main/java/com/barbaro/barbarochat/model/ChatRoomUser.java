package com.barbaro.barbarochat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class ChatRoomUser {
	
	@Getter
	private final String username;
	
	@Getter
	private String userId;
}
