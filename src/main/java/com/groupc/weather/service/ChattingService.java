package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.WebSocketSession;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.chatting.ChattingUserNumberDto;
import com.groupc.weather.dto.request.chatting.DeleteChattingRoomDto;
import com.groupc.weather.dto.response.chatting.GetChattingListResponseDto;
import com.groupc.weather.dto.response.chatting.GetChattingMessageListResponseDto;

public interface ChattingService {

    public ResponseEntity<? super GetChattingListResponseDto> getChattingList(AuthenticationObject authenticationObject);
    public ResponseEntity<? super GetChattingMessageListResponseDto> getChattingMessageList(AuthenticationObject authenticationObject, String roomId);

    public ResponseEntity<ResponseDto> createChattingRoom(AuthenticationObject authenticationObject, ChattingUserNumberDto dto);
    public ResponseEntity<ResponseDto> deleteChattingRoom(AuthenticationObject authenticationObject, DeleteChattingRoomDto dto);
    public <T> void sendMessage(WebSocketSession session, T message);
    // public ResponseEntity<ResponseDto> sendMessage(WebSocketSession session, TextMessage message);

}
