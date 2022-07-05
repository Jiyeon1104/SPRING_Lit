package com.example.lit.domain.vo.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserFileVO {
    private Long fileNumber;
    private String uuid;
    private String uploadPath;
    private String name;
    private String image;
    private Long userNumber;
}
