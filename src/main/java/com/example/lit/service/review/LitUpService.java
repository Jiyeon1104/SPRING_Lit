package com.example.lit.service.review;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.review.*;
import com.example.lit.domain.vo.user.AlertDTO;

import java.util.List;

public interface LitUpService {
    // 좋아요
    public void registerLike(LikeVO likeVO);
    // 좋아요 취소(작성자)
    public void removeLike(LikeVO likeVO);
    // 좋아요 갯수 카운트
    public Long getLikeTotal(Long reviewNumber);
    // 좋아요 확인
    public int getCheckLike(Long userNumber, Long reviewNumber);

    //댓글 추가
    public void registerReply(ReplyVO replyVO);
    //댓글 삭제
    public boolean removeReply(ReplyVO replyVO);
    //댓글 수정
    public boolean modifyReply(ReplyVO replyVO);
    //댓글 목록
    public List<ReplyVO> getReplyList(Criteria criteria, Long reviewNumber);
    //댓글 개수
    public int getTotalReply(Long reviewNumber);


    // 신고하기
    public void registerReport(ReportVO reportVO);
    // 신고 취소(작성자)
    public void removeReport(Long reportNumber);


    //    리뷰 목록 가져오기
    public List<ReviewDTO> getList(ListDTO listDTO);
    //    리뷰 등록
    public void register(ReviewVO reviewVO);
    //    리뷰 상세보기
    public ReviewVO read(Long reviewNumber);
    //    리뷰 삭제
    public boolean remove(Long reviewNumber);
    //    리뷰 수정
    public boolean modify(ReviewVO reviewVO);
    //    리뷰 전체 개수
    public int getTotal();
    //    성공, 실패용 인증 수 카운트
    public int resultCount();
    //    프로젝트 상세보기(리뷰 작성용)
    public ProjectDTO readForReview(Long projectNumber, Long userNumber);
    //    나의 프로젝트 가져오기(리뷰 작성용)
    public List<ProjectFileVO> getMyProject(Long userNumber);


    // 사진 삭제
    public void removeImg(Long reviewNumber);
    // 사진 수정
    public void modifyImg(ReviewFileVO reviewFileVO);
    // 사진 리스트
    public List<ReviewFileVO> getImgs(Long reviewNumber);
    // DB에 없는 이미지 삭제
    public List<ReviewFileVO> getOldFiles();


    //  인증글 검색(관리자)
    public List<ReviewDTO> searchReview(SearchDTO searchDTO);
    //리포트 검색(관리자)
    public List<ReportDTO> searchReport(SearchDTO searchDTO);
    //  오늘 등록된 인증글 수
    public int getTotalTodayReview();
    //오늘 등록된 리포트 토탈
    public int getTotalTodayReport();
    //  차트 정보
    public Long getReviewChart(String date);
    //  차트 정보
    public Long getReportChart(String date);
    // 리뷰 목록 가져오기2(테스트)
    public List<ReviewDTO> getMainList(ListDTO listDTO);
    // 3번째 메달 - 첫 lit up 작성하기
    public int getTotalByUserNumber(Long userNumber);

    // 알림 목록 가져오기
    public List<AlertDTO> getAlertList(Long userNumber);
    //마이페이지 리뷰 리스트
    public List<ReviewDTO> getMyList(ListDTO listDTO);

}
