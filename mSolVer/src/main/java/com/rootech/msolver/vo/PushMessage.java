package com.rootech.msolver.vo;

import java.awt.TrayIcon.MessageType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PushMessage {
//    private String chatRoomId;
//    private String writer;
    private String message;
    private MessageType type;
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
    
    
}