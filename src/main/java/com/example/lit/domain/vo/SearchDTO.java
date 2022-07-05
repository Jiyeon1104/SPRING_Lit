package com.example.lit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Data
@AllArgsConstructor
public class SearchDTO {
    private String startDate;
    private String endDate;
    private String kakao;
    private String category;
    private Long status;

    private String order;
    private boolean desc;

    private int pageNum;
    private int amount;
    private String type;
    private String keyword;


    public SearchDTO() {
        this(1, 10);
    }

    public SearchDTO(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    //쿼리스트링 만들어서 반환하는 메소드
    public String getListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.amount)
                .queryParam("type", this.type)
                .queryParam("keyword", this.keyword);
        return builder.toUriString(); //쿼리 스트링 반환
    }

}
