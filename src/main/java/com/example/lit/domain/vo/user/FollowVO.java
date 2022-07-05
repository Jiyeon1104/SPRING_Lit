package com.example.lit.domain.vo.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FollowVO {
    private Long followingNumber;
    private Long followerNumber;
    private String registerDate;
}
