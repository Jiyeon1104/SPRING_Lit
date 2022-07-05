package com.example.lit.controller;

import com.example.lit.domain.dao.message.ChatRoomRepository;
import com.example.lit.domain.vo.messsage.MessageDTO;
import com.example.lit.domain.vo.messsage.MessageRoom;
import com.example.lit.domain.vo.messsage.MessageVO;
import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.service.User.UserService;
import com.example.lit.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/*")
public class MessageRestController {
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

//    public static List<MessageVO> messageList = new ArrayList<MessageVO>();

    @PostMapping("/send")
    public void send(@RequestBody MessageVO messageVO){
        log.info("--------------------------------------------");
        log.info("메세지 보내기");
        log.info("--------------------------------------------");

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        messageVO.setMessageDate(sdf.format(now));

        // 메세지 입력할 때마다
        // static으로 선언한 리스트에 add해줌
//        messageList.add(messageVO);

        // 입력 할 때마다 db 저장
        messageService.register(messageVO);
    }

    @GetMapping("/getFollowerList/{userNumber}")
    public List<UserVO> getFollowerList(@PathVariable Long userNumber){
        return messageService.getFollowerList(userNumber);
    }

    @GetMapping("/searchFollower/{keyword}/{userNumber}")
    public List<UserVO> searchFollower(@PathVariable String keyword, @PathVariable Long userNumber){
        return messageService.searchFollower(keyword, userNumber);
    }

    @GetMapping("/getMessageList/{sendUserNumber}/{receiveUserNumber}/{pageNum}")
    public List<MessageDTO> getMessageList(@PathVariable("sendUserNumber") Long sendUserNumber,
                                          @PathVariable("receiveUserNumber") Long receiveUserNumber,
                                           @PathVariable("pageNum") Long pageNum){

        MessageDTO messageDTO = new MessageDTO();

        if(messageService.findRoom(sendUserNumber, receiveUserNumber) == 0){
            MessageRoom messageRoom = MessageRoom.create(sendUserNumber, receiveUserNumber);
            messageService.newRoom(messageRoom);
            messageDTO.setNewRoomCheck(true);
        }

        String roomId = messageService.getRoom(sendUserNumber, receiveUserNumber).getRoomId();

        messageDTO.setSendUserNumber(sendUserNumber);
        messageDTO.setReceiveUserNumber(receiveUserNumber);
        messageDTO.setPageNum(pageNum);
        messageDTO.setRoomId(roomId);

        return messageService.getMessageList(messageDTO);
    }

    @GetMapping("/getUserProfileImage/{userNumber}")
    public UserFileVO getUserProfileImage(@PathVariable("userNumber") Long userNumber){
        return userService.getImg(userNumber);
    }
    ////////////////////////////////////////////////////////////

    @GetMapping("/getRoomId/{sendUserNumber}/{receiveUserNumber}")
    public String getRoomId(@PathVariable("sendUserNumber") Long sendUserNumber, @PathVariable("receiveUserNumber") Long receiveUserNumber){

        String roomId = messageService.getRoom(sendUserNumber, receiveUserNumber).getRoomId();

        return messageService.getRoom(sendUserNumber, receiveUserNumber).getRoomId();
    }






}
