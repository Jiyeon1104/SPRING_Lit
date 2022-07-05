package com.example.lit.domain.vo.project;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class ProjectVO {
    private Long projectNumber;
    private String title;
    private String category;
    private String content;
    private String authentication;
    private Long applyCount;
    private Long userNumber;
    private String startDate;
    private String endDate;
    private Long status;
    private String registerDate;


    private ProjectFileVO projectFile;

}
