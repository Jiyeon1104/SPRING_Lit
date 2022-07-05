package com.example.lit.controller;

import com.example.lit.domain.vo.review.ReviewFileVO;
import com.example.lit.domain.vo.user.AlertDTO;
import com.example.lit.service.User.UserService;
import com.example.lit.service.review.LitUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/alert")
public class AlterController {
    @Autowired
    private LitUpService litUpService;

    @Autowired
    private UserService userService;

    // 알림 목록
    @GetMapping("/get/{userNumber}")
    public List<AlertDTO> getLikeList(@PathVariable("userNumber") Long userNumber){
        log.info("--------------------------------------------------------");
        List<AlertDTO> alertDTOS = litUpService.getAlertList(userNumber);

        for(AlertDTO alertDTO : alertDTOS) {
            alertDTO.setUserFileVO(userService.getImg(alertDTO.getUserNumber()));
            if(alertDTO.getTypeAlert().equals("like")){
                log.info("============================================================");
                List<ReviewFileVO> reviewFileVOS = litUpService.getImgs(alertDTO.getReviewNumber());
                alertDTO.setReviewFileVO(reviewFileVOS.get(0));
            }
        }

        return alertDTOS;
    }
}
