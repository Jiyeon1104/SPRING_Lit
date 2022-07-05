package com.example.lit.mapper.review;

import com.example.lit.domain.vo.review.LikeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    // 좋아요
    public void insert(LikeVO likeVO);
    // 좋아요(취소)
    public void delete(LikeVO likeVO);
    // 좋아요 갯수 카운트
    public Long getTotal(Long reviewNumber);
    // 좋아요 확인
    public int checkLike(Long userNumber, Long reviewNumber);

}
