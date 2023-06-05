package com.groupc.weather.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.chatting.ChattingUserNumberDto;
import com.groupc.weather.dto.request.chatting.DeleteChattingRoomDto;
import com.groupc.weather.dto.response.chatting.GetChattingListResponseDto;
import com.groupc.weather.dto.response.chatting.GetChattingMessageListResponseDto;
import com.groupc.weather.service.ChattingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/chatting")
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;

    // 채팅 리스트 보기
    @GetMapping("/view")
    public ResponseEntity<? super GetChattingListResponseDto> getChattingList(
        @AuthenticationPrincipal AuthenticationObject authenticationObject) {
        ResponseEntity<? super GetChattingListResponseDto> response = chattingService.getChattingList(authenticationObject);
        return response;
    }

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createChattingRoom(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody ChattingUserNumberDto dto
    ) {
        ResponseEntity<ResponseDto> response = chattingService.createChattingRoom(authenticationObject, dto);
        return response;
    }

    // 채팅 메시지 리스트 보기
    @GetMapping("/view/message/{roomId}")
    public ResponseEntity<? super GetChattingMessageListResponseDto> getChattingMessageList(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @PathVariable("roomId") String roomId
    ) {
        ResponseEntity<? super GetChattingMessageListResponseDto> response = chattingService.getChattingMessageList(authenticationObject, roomId);
        return response;
    }
    
    // 채팅 방 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteChattingRoom(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody DeleteChattingRoomDto dto
    ) {
        ResponseEntity<ResponseDto> response = chattingService.deleteChattingRoom(authenticationObject,dto);
        return response;
    }

}
    

