package com.example.lit.mapper;


import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReviewDTO;
import com.example.lit.domain.vo.review.ReviewVO;
import com.example.lit.mapper.review.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ReviewMapperTests {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    public void searchReviewTest(){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setCategory("hobby");
        searchDTO.setKeyword("21");
        searchDTO.setType("user_number");

        reviewMapper.searchReview(searchDTO).stream().map(ReviewDTO::toString).forEach(log::info);
    }

    @Test
    public void getTotalTodayTest(){
        log.info(String.valueOf(reviewMapper.getTotalToday()));
    }

    @Test
    public void getMainListTest(){
        ListDTO listDTO = new ListDTO();
        listDTO.setOrder("new");
        reviewMapper.getMainList(listDTO).stream().map(ReviewDTO::toString).forEach(log::info);
    }

    @Test
    public void getMyListTest(){
        ListDTO listDTO = new ListDTO();
        listDTO.setUserNumber(3L);
        listDTO.setPageNum(1);
        listDTO.setAmount(10);
        reviewMapper.getMyList(listDTO).stream().map(ReviewDTO::toString).forEach(log::info);
    }
}
