package com.example.lit.domain.vo.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDTO {
    private Long userNumber;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String content;
    private Long status;
    private String kakao;
    private String registerDate;
    private Long achievementNumber;
    private Long total;

    private UserFileVO userFileList;
}
