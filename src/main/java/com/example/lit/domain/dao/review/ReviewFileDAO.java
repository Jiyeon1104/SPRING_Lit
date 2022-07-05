package com.example.lit.domain.dao.review;

import com.example.lit.domain.vo.review.ReviewFileVO;
import com.example.lit.mapper.review.ReviewFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewFileDAO {
    private final ReviewFileMapper reviewFileMapper;

    // 사진 추가
    public void register(ReviewFileVO reviewFileVO){ reviewFileMapper.insert(reviewFileVO); }
    // 사진 삭제
    public void remove(Long reviewNumber){ reviewFileMapper.delete(reviewNumber); }
    // 사진 수정
    public void modify(ReviewFileVO reviewFileVO){ reviewFileMapper.update(reviewFileVO); }
    // 사진 리스트
    public List<ReviewFileVO> getImgs(Long reviewNumber){ return reviewFileMapper.getImgs(reviewNumber); }
    // DB에 없는 이미지 삭제
    public List<ReviewFileVO> getOldFiles(){ return reviewFileMapper.getOldFiles(); }
}
