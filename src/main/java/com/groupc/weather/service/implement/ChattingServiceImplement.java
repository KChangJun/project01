package com.groupc.weather.service.implement;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.chatting.ChattingUserNumberDto;
import com.groupc.weather.dto.request.chatting.DeleteChattingRoomDto;
import com.groupc.weather.dto.response.chatting.GetChattingListResponseDto;
import com.groupc.weather.dto.response.chatting.GetChattingMessageListResponseDto;
import com.groupc.weather.entity.ChattingRoomEntity;
import com.groupc.weather.entity.resultSet.ChattingListResultSet;
import com.groupc.weather.entity.resultSet.ChattingMessageListResultSet;
import com.groupc.weather.repository.ChattingMessageRepository;
import com.groupc.weather.repository.ChattingRoomRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.ChattingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingServiceImplement implements ChattingService {

    private final UserRepository userRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingMessageRepository chattingMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<? super GetChattingListResponseDto> getChattingList(AuthenticationObject authenticationObject) {
        GetChattingListResponseDto body = null;
        String email = authenticationObject.getEmail();

        try {
            boolean isExistUserEmail = userRepository.existsByEmail(email);
            if (!isExistUserEmail) return CustomResponse.noPermissions();

            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            
            List<ChattingListResultSet> resultSet = chattingMessageRepository.getChattingList(userNumber);
            body = new GetChattingListResponseDto(resultSet);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetChattingMessageListResponseDto> getChattingMessageList(
        AuthenticationObject authenticationObject, String roomId
    ) {
        GetChattingMessageListResponseDto body = null;
        String email = authenticationObject.getEmail();

        try {
            boolean isExistUserEmail = userRepository.existsByEmail(email);
            if (!isExistUserEmail) return CustomResponse.noPermissions();

            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            
            List<ChattingMessageListResultSet> resultSet = chattingMessageRepository.getChattingMessageList(userNumber, roomId);
            body = new GetChattingMessageListResponseDto(resultSet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<ResponseDto> createChattingRoom(AuthenticationObject authenticationObject,
        ChattingUserNumberDto dto
    ) {
        try {
            // 채팅을 신청한 쪽에서 먼저 방을 만들고
            Integer createUserNumber = userRepository.findByEmail(authenticationObject.getEmail()).getUserNumber();
            String roomId = UUID.randomUUID().toString();
            ChattingRoomEntity chattingRoomEntity = new ChattingRoomEntity(roomId, createUserNumber);
            ChattingRoomEntity chattingRoomEntity2 = new ChattingRoomEntity(roomId, dto.getUserNumber());
            chattingRoomRepository.save(chattingRoomEntity);
            chattingRoomRepository.save(chattingRoomEntity2);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    @Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deleteChattingRoom(AuthenticationObject authenticationObject,
            DeleteChattingRoomDto dto) {
        try {

            List<ChattingRoomEntity> chattingRoomEntitys = chattingRoomRepository.findByRoomId(dto.getRoomId());
            chattingRoomRepository.deleteAll(chattingRoomEntitys);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }




    
    
}
