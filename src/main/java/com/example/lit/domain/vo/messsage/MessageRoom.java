package com.example.lit.domain.vo.messsage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@Data
public class MessageRoom {
    private String roomId;
    private Long sendUserNumber;
    private Long receiveUserNumber;

    private Set<WebSocketSession> sessions = new HashSet<>();

    public static MessageRoom create(Long sendUserNumber, Long receiveUserNumber){
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.roomId = UUID.randomUUID().toString();
        messageRoom.sendUserNumber = sendUserNumber;
        messageRoom.receiveUserNumber = receiveUserNumber;
        return messageRoom;
    }

    public void handleMessage(WebSocketSession session, MessageVO messageVO,
                              ObjectMapper objectMapper) throws IOException {
        if(messageVO.getType() == MessageType.ENTER){
            sessions.add(session);
        }
        else if(messageVO.getType() == MessageType.LEAVE){
            sessions.remove(session);
        }
        else{
            messageVO.setContent(messageVO.getSendUserNumber() + " : " + messageVO.getContent());
        }
        send(messageVO,objectMapper);
    }

    private void send(MessageVO messageVO, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(messageVO.getContent()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }





}
