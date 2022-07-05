package com.example.lit.domain.vo.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AlertVO {
    private Long alertNumber;
    private Long alertUser;
    private Long userNumber;
    private String typeAlert;
    private Long reviewNumber;
    private String registerDate;
}
