package com.example.lit.mapper;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.review.ReviewVO;
import com.example.lit.mapper.review.ReportMapper;
import com.example.lit.mapper.review.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ReviewMapperTest {
    @Autowired
    private ReviewMapper reviewMapper;

//    @Test
//    public void getListTest(){
//        reviewMapper.getList(new Criteria(1, 10),"newproject").stream().map(ReviewVO::toString).forEach(log::info);
//    }

    @Test
    public void registerTest() {
        reviewMapper.getForAlert(1L);
    }

}
