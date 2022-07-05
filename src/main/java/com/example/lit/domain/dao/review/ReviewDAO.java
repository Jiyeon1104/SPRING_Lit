package com.example.lit.domain.dao.review;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReviewDTO;
import com.example.lit.domain.vo.review.ReviewVO;
import com.example.lit.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDAO {
    private final ReviewMapper reviewMapper;

    //    리뷰 목록 가져오기
    public List<ReviewDTO> getList(ListDTO listDTO){ return reviewMapper.getList(listDTO); }
    //    리뷰 등록
    public void register(ReviewVO reviewVO){ reviewMapper.insert(reviewVO); }
    //    리뷰 상세보기
    public ReviewVO read(Long reviewNumber){ return reviewMapper.get(reviewNumber); }
    //    리뷰 삭제
    public boolean remove(Long reviewNumber){ return reviewMapper.delete(reviewNumber) != 0; }
    //    리뷰 수정
    public boolean modify(ReviewVO reviewVO){ return reviewMapper.update(reviewVO) != 0; }
    //    리뷰 전체 개수
    public int getTotal(){ return reviewMapper.getTotal(); }
    //    성공, 실패용 인증 수 카운트
    public int resultCount(){ return reviewMapper.resultCount(); }
    //  인증글 검색(관리자)
    public List<ReviewDTO> searchReview(SearchDTO searchDTO){ return reviewMapper.searchReview(searchDTO); }
    //  오늘 등록된 인증글 수
    public int getTotalToday(){ return reviewMapper.getTotalToday(); }
    //  차트 정보
    public Long getReviewChart(String date){ return reviewMapper.getReviewChart(date); }
    // 리뷰 목록 가져오기2(테스트)
    public List<ReviewDTO> getMainList(ListDTO listDTO) { return reviewMapper.getMainList(listDTO); }

    // 3번째 메달 - 첫 lit up 작성하기
    public int getTotalByUserNumber(Long userNumber) { return reviewMapper.getTotalByUserNumber(userNumber); }
    // 리뷰 작성자 번호 가져오기 알림
    public Long getReviewNumberForAlert(Long reviewNumber) { return reviewMapper.getForAlert(reviewNumber); }

    // 내정보 변경시 닉네임 바꾸기
    public int updateNicknameForReview(Long userNumber, String nickname){ return reviewMapper.updateNickname(userNumber, nickname);}

    //마이페이지 리뷰 리스트
    public List<ReviewDTO> getMyList(ListDTO listDTO){ return reviewMapper.getMyList(listDTO); }
}
