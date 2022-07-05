package com.example.lit.domain.vo.project;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ParticipationVO {
    private Long projectNumber;
    private Long userNumber;
    private Long status;
    private String registerDate;
}
