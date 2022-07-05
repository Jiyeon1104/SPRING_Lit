package com.example.lit.mapper.review;

import com.example.lit.domain.vo.review.LikeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LikeMapperTests {
    @Autowired
    private LikeMapper likeMapper;

    @Test
    public void insertTest() {
        LikeVO likeVO = new LikeVO();
        likeVO.setUserNumber(1L);
        likeVO.setReviewNumber(2L);
        likeMapper.insert(likeVO);
    }

}
