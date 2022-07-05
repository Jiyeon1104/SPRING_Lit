package com.example.lit.mapper.review;

import com.example.lit.domain.vo.review.ReviewFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewFileMapper {
    // 사진 추가
    public void insert(ReviewFileVO reviewFileVO);
    // 사진 삭제
    public void delete(Long reviewNumber);
    // 사진 수정
    public void update(ReviewFileVO reviewFileVO);
    // 사진 리스트
    public List<ReviewFileVO> getImgs(Long reviewNumber);
    // DB에 없는 이미지 삭제
    public List<ReviewFileVO> getOldFiles();
}
