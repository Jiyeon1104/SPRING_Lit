package com.example.lit.domain.vo.review;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ReplyVO {
    private Long replyNumber;
    private Long reviewNumber;
    private Long userNumber;
    private String content;
    private String registerDate;
    private String nickname;


}
