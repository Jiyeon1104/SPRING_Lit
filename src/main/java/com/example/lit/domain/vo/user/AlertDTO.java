package com.example.lit.domain.vo.user;

import com.example.lit.domain.vo.review.ReviewFileVO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AlertDTO {
    private Long alertUser;
    private Long userNumber;
    private String typeAlert;
    private Long reviewNumber;
    private String registerDate;
    private String nickName;
    private UserFileVO userFileVO;
    private ReviewFileVO reviewFileVO;
}
