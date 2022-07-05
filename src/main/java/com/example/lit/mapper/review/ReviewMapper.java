package com.example.lit.mapper.review;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReviewDTO;
import com.example.lit.domain.vo.review.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    //    리뷰 목록 가져오기
    public List<ReviewDTO> getList(ListDTO listDTO);
    //    리뷰 등록
    public void insert(ReviewVO reviewVO);
    //    리뷰 상세보기
    public ReviewVO get(Long reviewNumber);
    //    리뷰 삭제
    public int delete(Long reviewNumber);
    //    리뷰 수정
    public int update(ReviewVO reviewVO);
    //    리뷰 전체 개수
    public int getTotal();
    //    성공, 실패용 인증 수 카운트
    public int resultCount();
    //  인증글 검색(관리자)
    public List<ReviewDTO> searchReview(SearchDTO searchDTO);
    //  오늘 등록된 인증글 수
    public int getTotalToday();
    //  차트 정보
    public Long getReviewChart(String date);
    // 리뷰 목록 가져오기2(테스트)
    public List<ReviewDTO> getMainList(ListDTO listDTO);

    // 3번째 메달 - 첫 lit up 작성하기
    public int getTotalByUserNumber(Long userNumber);

    // project 리뷰 토탈
    public Long reviewTotal(Long projectNumber);

    // 리뷰 작성자 번호 가져오기 알림
    public Long getForAlert(Long reviewNumber);

    public int updateNickname(Long userNumber, String nickname);

    //마이페이지 리뷰 리스트
    public List<ReviewDTO> getMyList(ListDTO listDTO);

}
