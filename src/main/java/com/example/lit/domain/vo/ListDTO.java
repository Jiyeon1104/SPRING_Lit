package com.example.lit.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ListDTO {
    private String category;
    private String order;
    private int pageNum;
    private int amount;
    private Long projectNumber;
    private Long userNumber;


    public ListDTO() {
        this(1, 12);
    }

    public ListDTO(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}
