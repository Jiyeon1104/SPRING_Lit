package com.example.lit.domain.dao.message;


import com.example.lit.domain.vo.messsage.MessageRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, MessageRoom> messageRoomMap;

    @PostConstruct
    private void init(){
        messageRoomMap = new LinkedHashMap<>();
    }

    public MessageRoom createMessageRoom(Long sendUserNumber, Long receiveUserNumber){
        MessageRoom messageRoom = MessageRoom.create(sendUserNumber, receiveUserNumber);
        messageRoomMap.put(messageRoom.getRoomId(), messageRoom);
        return messageRoom;
    }

    public List<MessageRoom> findAllRoom(){
        List messageRooms = new ArrayList<>(messageRoomMap.values());
        Collections.reverse(messageRooms);
        return messageRooms;
    }

    public MessageRoom findRoomById(String roomId){
        return messageRoomMap.get(roomId);
    }



}
