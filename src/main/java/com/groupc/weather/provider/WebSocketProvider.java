package com.groupc.weather.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.entity.ChattingMessageEntity;
import com.groupc.weather.entity.ChattingRoomEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.repository.ChattingMessageRepository;
import com.groupc.weather.repository.ChattingRoomRepository;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.ChattingService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
class SessionGroup {
    private String roomId;
    private WebSocketSession session;
}

@Getter
@Component
@RequiredArgsConstructor
public class WebSocketProvider extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChattingService chattingService;
    private final ChattingRoomRepository chattingRoomRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final ChattingMessageRepository chattingMessageRepository;

    private List<SessionGroup> sessionList = new ArrayList<>();

    //* 연결 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        boolean isValid = validated(session);
        System.out.println(isValid);
        if (!isValid){
            System.out.println("연결실패");
            session.close();
            return;
        }
        String token = session.getHandshakeHeaders().getFirst("Authorization");    
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        AuthenticationObject authenticationObject = jwtProvider.validate(token);
        UserEntity userEntity = userRepository.findByEmail(authenticationObject.getEmail());
        List<ChattingRoomEntity> chattingRoomEntities = chattingRoomRepository.findByRoomId(roomId);
        Integer userNumber = null;
        for(ChattingRoomEntity chattingRoomEntity : chattingRoomEntities){
            if(!chattingRoomEntity.getUserNumber().equals(userEntity.getUserNumber())){
                userNumber=chattingRoomEntity.getUserNumber();
            }
        }

        List<ChattingMessageEntity> chattingMessageEntities = chattingMessageRepository.findByNotViewList(roomId,userNumber);
            for(ChattingMessageEntity chattingMessageEntity : chattingMessageEntities){
                chattingMessageEntity.setView(true);
                chattingMessageRepository.save(chattingMessageEntity);

            }

        sessionList.add(new SessionGroup(roomId, session));
            System.out.println(roomId + " / " + authenticationObject.getEmail() + "Web Socket Connected!!");
    }

    public void handlerActions(WebSocketSession session, TextMessage message, ChattingService chattingService)
            throws IOException {

        String token = session.getHandshakeHeaders().getFirst("Authorization");
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        AuthenticationObject authenticationObject = jwtProvider.validate(token);

        UserEntity userEntity = userRepository.findByEmail(authenticationObject.getEmail());

        for (SessionGroup sessionGroup : sessionList) {
            if (sessionGroup.getRoomId().equals(roomId)) {
                sessionGroup.getSession().sendMessage(message);
            }
        }

        ChattingMessageEntity chattingMessageEntity = new ChattingMessageEntity(roomId, message,
                userEntity.getUserNumber());
        chattingMessageRepository.save(chattingMessageEntity);

    }

    // * 메세지 송수신 */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        handlerActions(session, message, chattingService);

    }

    //* 연결 해제 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }


    private boolean validated(WebSocketSession session) throws Exception {
        String token = session.getHandshakeHeaders().getFirst("Authorization");
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        AuthenticationObject authenticationObject = jwtProvider.validate(token);
        if (authenticationObject == null) {
            session.close();
            return false;
        }
        // todo: 존재하는 유저인지?
        boolean existedUser = userRepository.existsByEmail(authenticationObject.getEmail());
        System.out.println("existedUser:" + existedUser);
        if(!existedUser){
            return false;
        }
      
        // todo: 존재하는 방인지?
        boolean existedRoomId = chattingRoomRepository.existsByRoomId(roomId);
        System.out.println("existedRoomId:" + existedRoomId);
        if(!existedRoomId){
            return false;
        }

        UserEntity userEntity = userRepository.findByEmail(authenticationObject.getEmail());
 
        List<ChattingRoomEntity> chattingRoomEntities = chattingRoomRepository.findByRoomId(roomId);
        boolean equalsUserNumber= false;
        System.out.println("equalsUserNumber: " + equalsUserNumber);
        for(ChattingRoomEntity chattingRoomEntity : chattingRoomEntities){
            if(chattingRoomEntity.getUserNumber().equals(userEntity.getUserNumber())){
                equalsUserNumber=true;
            }
            
        }
        if(!equalsUserNumber) return false;


        

        return true;
    }

}
