package com.example.lit.domain.vo.user.achievement;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AchievementVO {
    private Long achievementNumber; 
    private Long userNumber;
    private String medal;
}
