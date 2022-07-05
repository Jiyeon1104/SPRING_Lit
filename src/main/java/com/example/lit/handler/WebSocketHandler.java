package com.example.lit.handler;

import com.example.lit.domain.dao.message.ChatRoomRepository;
import com.example.lit.domain.dao.message.MessageDAO;
import com.example.lit.domain.vo.messsage.MessageRoom;
import com.example.lit.domain.vo.messsage.MessageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    public static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 전송 = {} : {}",session,message.getPayload());
        String msg = message.getPayload();

//        MessageVO messageVO = objectMapper.readValue(msg,MessageVO.class);
//        MessageRoom messageRoom = chatRoomRepository.findRoomById(messageVO.getRoomId());
//        messageRoom.handleMessage(session,messageVO,objectMapper);

        for(WebSocketSession sess: list){
            sess.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + "접속");
        list.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + "접속 끝");
        list.remove(session);
    }
}
