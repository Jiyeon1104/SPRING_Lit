package com.example.lit.domain.vo.review;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ReportDTO {
    private Long reportNumber;
    private Long userNumber;
    private Long reviewNumber;
    private String reason;

    private String reportRegisterDate;
    private Long reviewUserNumber;
    private String reviewRegisterDate;
    private String reviewEmail;
    private String reportEmail;
    private String category;
    private Long total;
}
