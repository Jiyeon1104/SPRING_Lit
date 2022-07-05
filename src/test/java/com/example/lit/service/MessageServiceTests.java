package com.example.lit.service;

import com.example.lit.domain.dao.message.MessageDAO;
import com.example.lit.domain.vo.messsage.MessageDTO;
import com.example.lit.domain.vo.messsage.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MessageServiceTests {
    @Autowired
    private MessageDAO messageDAO;

    @Test
    public void getListTest(){

        messageDAO.getList("1번방").stream().map(MessageVO::toString).forEach(log::info);

    }

    @Test
    public void insertTest(){
        log.info("메세지 입력");
        MessageVO messageVO = new MessageVO();
        messageVO.setSendUserNumber(1L);
        messageVO.setReceiveUserNumber(10L);
        messageVO.setRoomId("1번방");
        messageVO.setContent("ddkdjfe");
        messageVO.setMessageDate("2022-06-19");
        messageDAO.register(messageVO);

    }

    @Test
    public void getFollowerListTest(){
        messageDAO.getFollowerList(1L);
    }

    @Test
    public void getReceiveUserNumberTest() { messageDAO.getReceiveUserNumber(1L); }

    @Test
    public void getRecentMessageTest(){ messageDAO.getRecentMessage(5L); }

    @Test
    public void getMessageListTest(){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSendUserNumber(1L);
        messageDTO.setReceiveUserNumber(10L);
        messageDAO.getMessageList(messageDTO);
    }


}
