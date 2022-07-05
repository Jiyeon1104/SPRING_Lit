package com.example.lit.controller;


import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.service.User.UserService;
import com.example.lit.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/message/*")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/message")
    public String message(HttpSession session, Model model){
        log.info("******************************");
        log.info("ProjectController : message");
        log.info("******************************");

//        userNumber = 1L;
        Long userNumber = Long.parseLong(session.getAttribute("userNumber").toString());

        //메세지 페이지 이동할 때 메시지 한 유저 닉네임과 최근 메시지를 담아준다.
        List<Map<String, Object>> sendList = new ArrayList<>();
        List<Map<String, Object>> receiveList = new ArrayList<>();
        List<UserFileVO> sendImageList = new ArrayList<>();
        List<UserFileVO> receiveImageList = new ArrayList<>();

        List<Long> receiveNumbers = messageService.getReceiveUserNumber(userNumber);
        List<Long> sendNumbers = messageService.getSendUserNumber(userNumber);

        for (Long receiveUser : receiveNumbers) {
            //유저 번호로 프로필 사진 정보 넣어주기//
            sendList.add(messageService.getRecentMessage(receiveUser));
            sendImageList.add(userService.getImg(receiveUser));
        }

        for (Long sendUser : sendNumbers){
            //유저 번호로 프로필 사진 정보 넣어주기//
            receiveList.add(messageService.getRecentReceiveMessage(sendUser));
            receiveImageList.add(userService.getImg(sendUser));
        }

        model.addAttribute("recentSendMessage", sendList);
        model.addAttribute("recentReceiveMessage", receiveList);
        model.addAttribute("sendImageList",sendImageList);
        model.addAttribute("receiveImageList", receiveImageList);

        return "/message/message";
    }

}
