package com.example.lit.mapper;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.review.ReplyVO;
import com.example.lit.mapper.review.ReplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ReplyMapperTests {
    @Autowired
    private ReplyMapper replyMapper;


    @Test
    public void getListTest() {


        replyMapper.getList(new Criteria(1,10), 1L)
                .stream().map(ReplyVO::toString).forEach(log::info);
    }

    @Test
    public void insertTest() {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setContent("test");
        replyVO.setUserNumber(1L);
        replyVO.setReviewNumber(1L);
        replyMapper.insert(replyVO);
    }

}
