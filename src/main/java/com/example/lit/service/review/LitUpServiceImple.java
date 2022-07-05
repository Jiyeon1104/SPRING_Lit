package com.example.lit.service.review;

import com.example.lit.domain.dao.project.ProjectDAO;
import com.example.lit.domain.dao.project.ProjectFileDAO;
import com.example.lit.domain.dao.review.*;
import com.example.lit.domain.dao.user.achievement.AchievementDAO;
import com.example.lit.domain.dao.user.AlertDAO;
import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.review.*;
import com.example.lit.domain.vo.user.AlertDTO;
import com.example.lit.domain.vo.user.AlertVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LitUpServiceImple implements LitUpService{
    private final LikeDAO likeDAO;
    private final ReplyDAO replyDAO;
    private final ReportDAO reportDAO;
    private final ReviewDAO reviewDAO;
    private final ReviewFileDAO reviewFileDAO;
    private final ProjectDAO projectDAO;
    private final ProjectFileDAO projectFileDAO;
    private final AchievementDAO achievementDAO;
    private final AlertDAO alertDAO;

    @Override
    public void registerLike(LikeVO likeVO) {
        likeDAO.register(likeVO);
        
        // 좋아요 추가시 alert테이블에 같이 추가
        AlertVO alertVO = new AlertVO();
        alertVO.setAlertUser(reviewDAO.getReviewNumberForAlert(likeVO.getReviewNumber()));
        alertVO.setUserNumber(likeVO.getUserNumber());
        alertVO.setTypeAlert("like");
        alertVO.setReviewNumber(likeVO.getReviewNumber());

        log.info(alertVO.toString());
        alertDAO.insert(alertVO);
    }

    @Override
    public void removeLike(LikeVO likeVO) {
        // 좋아요 취소시 알림 삭제
        AlertVO alertVO = new AlertVO();
        alertVO.setAlertUser(reviewDAO.getReviewNumberForAlert(likeVO.getReviewNumber()));
        alertVO.setUserNumber(likeVO.getUserNumber());
        alertVO.setTypeAlert("like");
        alertVO.setReviewNumber(likeVO.getReviewNumber());
        alertDAO.remove(alertDAO.getAlertNumber(alertVO));

        likeDAO.remove(likeVO);
    }

    @Override
    public Long getLikeTotal(Long reviewNumber) {
        return likeDAO.getTotal(reviewNumber);
    }

    @Override
    public int getCheckLike(Long userNumber, Long reviewNumber) {
        return likeDAO.checkLike(userNumber, reviewNumber);
    }

    @Override
    public void registerReply(ReplyVO replyVO) {
        replyDAO.register(replyVO);
    }

    @Override
    public boolean removeReply(ReplyVO replyVO) {

        return replyDAO.remove(replyVO);
    }

    @Override
    public boolean modifyReply(ReplyVO replyVO) {
        return false;
    }

    @Override
    public List<ReplyVO> getReplyList(Criteria criteria, Long reviewNumber) {
        return replyDAO.getList(criteria, reviewNumber);
    }

    @Override
    public int getTotalReply(Long reviewNumber) {
        return replyDAO.getTotal(reviewNumber);
    }

    @Override
    public void registerReport(ReportVO reportVO) {
        reportDAO.register(reportVO);
    }


    @Override
    public List<ReviewDTO> getList(ListDTO listDTO) {
        List<ReviewDTO> result = reviewDAO.getList(listDTO).stream().map( review -> {
            List<ReviewFileVO> list = reviewFileDAO.getImgs(review.getReviewNumber());
            review.setReviewFileList(list);
            return review;
        }).collect(Collectors.toList());
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(ReviewVO reviewVO) {
        reviewDAO.register(reviewVO);
        if(reviewVO.getReviewFileList() != null) {
            reviewVO.getReviewFileList().forEach(reviewFileVO -> {
                reviewFileVO.setReviewNumber(reviewVO.getReviewNumber());
                reviewFileDAO.register(reviewFileVO);
            });
        }

        log.info(reviewVO.getUserNumber() + "############################################");
        Long ss = reviewVO.getUserNumber();
        log.info(ss + "!!!!!!!!!!!!!!!!!!!!!!!1");

        log.info(getTotalByUserNumber(ss) + "%%%%%%%%%%%%%%%%%%%%%%%%%%");
        // 3번째 메달 - 첫 lit up 작성하기
        if(getTotalByUserNumber(reviewVO.getUserNumber()) == 1){
            achievementDAO.insertMedal(reviewVO.getUserNumber(), "3");
        }

    }

    @Override
    public ReviewVO read(Long reviewNumber) {
        return reviewDAO.read(reviewNumber);
    }

    @Override
    public boolean remove(Long reviewNumber) {
        return reviewDAO.remove(reviewNumber);
    }

    @Override
    public boolean modify(ReviewVO reviewVO) {
        return false;
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public int resultCount() {
        return 0;
    }

    @Override
    public ProjectDTO readForReview(Long projectNumber, Long userNumber) {
        return projectDAO.getForReview(projectNumber, userNumber);
    }

    @Override
    public List<ProjectFileVO> getMyProject(Long userNumber) {
        return projectFileDAO.getMyProject(userNumber);
    }

    @Override
    public void removeImg(Long reviewNumber) {

    }

    @Override
    public void modifyImg(ReviewFileVO reviewFileVO) {

    }

    @Override
    public List<ReviewFileVO> getImgs(Long reviewNumber) {
        return reviewFileDAO.getImgs(reviewNumber);
    }

    @Override
    public List<ReviewFileVO> getOldFiles() {
        return null;
    }

    @Override
    public List<ReviewDTO> searchReview(SearchDTO searchDTO) {
        return reviewDAO.searchReview(searchDTO);
    }

    @Override
    public List<ReportDTO> searchReport(SearchDTO searchDTO) {
        return reportDAO.searchReport(searchDTO);
    }

    @Override
    public void removeReport(Long reportNumber) {
        reportDAO.remove(reportNumber);
    }

    @Override
    public int getTotalTodayReview() {
        return reviewDAO.getTotalToday();
    }

    @Override
    public int getTotalTodayReport() {
        return reportDAO.getTotalToday();
    }

    @Override
    public Long getReviewChart(String date) {
        return reviewDAO.getReviewChart(date);
    }

    @Override
    public Long getReportChart(String date) {
        return reportDAO.getReviewChart(date);
    }

    @Override
    public List<ReviewDTO> getMainList(ListDTO listDTO) {
        List<ReviewDTO> result = reviewDAO.getMainList(listDTO).stream().map( review -> {
            List<ReviewFileVO> list = reviewFileDAO.getImgs(review.getReviewNumber());
            review.setReviewFileList(list);
            return review;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public int getTotalByUserNumber(Long userNumber) { return reviewDAO.getTotalByUserNumber(userNumber); }
    @Override
    public List<AlertDTO> getAlertList(Long userNumber){
        return alertDAO.getList(userNumber);
    }

//    @Override
//    public List<ReviewVO> getMyList(Long userNumber) {
//        log.info("*********************************************");
//        log.info("LitUpService : getMyList");
//        log.info("*********************************************");
//        List<ReviewVO> result = new ArrayList<>();
//        for(ReviewVO review : reviewDAO.getMyList(userNumber)){
//            review.setReviewFileList(reviewFileDAO.getImgs(review.getReviewNumber()));
//            result.add(review);
//        }
//        result.stream().map(ReviewVO::toString).forEach(log::info);
//
//        return result;
//    }

    @Override
    public List<ReviewDTO> getMyList(ListDTO listDTO) {
        log.info("************************************e*********");
        log.info("LitUpService : getMyList");
        log.info("*********************************************");
        List<ReviewDTO> result = new ArrayList<>();
        for(ReviewDTO review : reviewDAO.getMyList(listDTO)){
            review.setReviewFileList(reviewFileDAO.getImgs(review.getReviewNumber()));
            review.setReplyCount((long)replyDAO.getTotal(review.getReviewNumber()));
            result.add(review);
        }
        result.stream().map(ReviewDTO::toString).forEach(log::info);

        return result;
    }
}
