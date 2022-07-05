package com.example.lit.domain.vo.review;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ReviewDTO{
    private Long reviewNumber;
    private Long userNumber;
    private Long projectNumber;
    private String content;
    private String registerDate;
    private String nickname;

    private List<ReviewFileVO> reviewFileList;

    private Long status;
    private String category;
    private String email;
    private Long total;
    private Long likeCount;
    private Long replyCount;
}
