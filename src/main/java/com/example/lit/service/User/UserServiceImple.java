package com.example.lit.service.User;

import com.example.lit.domain.dao.review.ReplyDAO;
import com.example.lit.domain.dao.review.ReviewDAO;
import com.example.lit.domain.dao.user.AlertDAO;
import com.example.lit.domain.dao.user.FollowDAO;
import com.example.lit.domain.dao.message.MessageDAO;
import com.example.lit.domain.dao.user.UserDAO;
import com.example.lit.domain.dao.user.UserFileDAO;
import com.example.lit.domain.dao.user.achievement.AchievementDAO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReviewDTO;
import com.example.lit.domain.vo.review.ReviewFileVO;
import com.example.lit.domain.vo.user.*;
import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.messsage.MessageVO;
import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.domain.vo.user.achievement.AchievementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImple implements UserService{
    private final AchievementDAO achievementDAO;
    private final FollowDAO followDAO;
    private final MessageDAO messageDAO;
    private final UserDAO userDAO;
    private final UserFileDAO userFileDAO;
    private final AlertDAO alertDAO;
    private final ReviewDAO reviewDAO;
    private final ReplyDAO replyDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO kakaoLogin(UserVO userVO) {

        // 회원가입을 해야하는 경우
        if(userDAO.dbEmailCheck(userVO.getEmail()) ==0){
           log.info(" UserService.java 이메일 중복 없어서 회원 가입 시켜야함 앞에서 기본키 줌?");
//            log.info("---------------------------------");
////            log.info(userDAO.kakaoLogin(userVO).getName());
////            log.info("userVO, UserService.java"+userDAO.kakaoLogin(userVO));
////            log.info("userVO, UserService.java"+userDAO.kakaoLogin(userVO).getUserNumber());
//            log.info("---------------------------------");
//            log.info("---------------------------------");

            // 셀렉트키로 userNumber 생성
            userDAO.kakaoLogin(userVO);

            log.info("==========================================");
            log.info("==========================================");
            log.info(userDAO.read(userVO.getUserNumber()) + "회원가입후 유저 정보 기본키 가져오는지 확인!!!");
            log.info("==========================================");
            log.info("==========================================");

            // 회원가입시 얻는 메달
            achievementDAO.insertMedal(userVO.getUserNumber(), "1");

            return userDAO.read(userVO.getUserNumber());
        }else { // 이미 가입되어 있어서 로그인이 되어야하는 경우

            // 카카오 회원가입은 userNumber를 받을 수 없어서 email을 통해서 찾아준다.
            Long userNumber = userDAO.kakaoRead(userVO.getEmail());

            log.info(" UserService.java 이미 가입된 애 로그인 시킬거임");
            log.info("---------------------------------");
            log.info("이지은이야? "+ userDAO.read(userNumber).getName());
            log.info("userVO,"+userDAO.read(userNumber));
            log.info("userVO, UserService.java 기본키 값"
                    +userDAO.read(userNumber).getUserNumber());
            log.info("---------------------------------");
            log.info("---------------------------------");

            return userDAO.read(userNumber);
        }
    }

    @Override
    public Long kakaoRead(String email) {
        return userDAO.kakaoRead(email);
    }

    @Override
    public boolean kakaoUpdate(UserVO userVO) {
        userDAO.kakaoUpdate(userVO);
        log.info("=================================================");
        log.info("=================================================");
        log.info(userVO.getUserNumber() + "서비스 : 카카오회원가입 수정!!!!!!!!!!");
        log.info("=================================================");
        log.info("=================================================");
        return true;
    }

    @Override
    public boolean dbEmailCheck(String email) {
        return userDAO.dbEmailCheck(email) != 1;
    }

    @Override
    public boolean dbNicknameCheck(String nickname) {
        return userDAO.dbNicknameCheck(nickname) != 1;
    }

    @Override
    public int MyReviewCnt(Long userNumber) { return userDAO.MyReviewCnt(userNumber); }

    @Override
    public int MyFollowerCnt(Long userNumber) { return userDAO.MyFollowerCnt(userNumber); }

    @Override
    public int MyFollowingCnt(Long userNumber) { return userDAO.MyFollowingCnt(userNumber); }

    @Override
    public List<UserVO> ModalFollower(Long userNumber) {
        List<UserVO> userVOS = userDAO.ModalFollower(userNumber);
        for(UserVO userVO : userVOS){
            userVO.setUserFileList(userFileDAO.getImg(userVO.getUserNumber()));
        }
        return userVOS;
    }

    @Override
    public List<UserVO> ModalFollowing(Long userNumber) {
        List<UserVO> userVOS = userDAO.ModalFollowing(userNumber);
        for(UserVO userVO : userVOS){
            userVO.setUserFileList(userFileDAO.getImg(userVO.getUserNumber()));
        }
        return userVOS;
    }

    @Override
    public void removeFollower(Long followerNumber, Long followingNumber) { userDAO.removeFollower(followerNumber, followingNumber); }

    @Override
    public boolean dbOldPwCheck(String password, Long userNumber) { return userDAO.dbOldPwCheck(password, userNumber); }

    @Override
    public UserVO getChangePwInfo(Long userNumber) { return userDAO.getChangePwInfo(userNumber); }

    @Override
    public UserVO login(String email, String pw) {
        return userDAO.login(email, pw);
    }

    @Override
    public UserVO read(Long userNumber) { return userDAO.read(userNumber); }

    @Override
    public boolean adminLogin(String email, String password) {
        return userDAO.adminLogin(email, password);
    }

    @Override
    public void remove(Long userNumber) {
        userDAO.remove(userNumber);
    }

    @Override
    public void modify(UserVO userVO) { userDAO.modify(userVO); }

    @Override
    public void modifyPw(Long userNumber, String newPassword) { userDAO.modifyPw(userNumber, newPassword); }

    @Override
    public void modifyMedal(UserVO userVO) {

    }

    @Override
    public void registerImg(UserFileVO userFileVO) {

    }

    @Override
    public void removeImg(Long userNumber) {
        userFileDAO.remove(userNumber);
    }

    @Override
    public void modifyImg(UserFileVO userFileVO) {
        if(userFileDAO.getImg(userFileVO.getUserNumber()) == null){
            userFileDAO.register(userFileVO);
        }else {
            userFileDAO.modify(userFileVO);
        }
    }

    @Override
    public UserFileVO getImg(Long userNumber) {
        return userFileDAO.getImg(userNumber);
    }

    @Override
    public List<UserFileVO> getOldFiles() {
        return null;
    }

    @Override
    public void follow(FollowVO followVO) {
        followDAO.register(followVO);

        // 알람
        AlertVO alertVO = new AlertVO();
        alertVO.setAlertUser(followVO.getFollowerNumber());
        alertVO.setUserNumber(followVO.getFollowingNumber());
        alertVO.setTypeAlert("follow");
        alertDAO.alertFollow(alertVO);
    }

    @Override
    public void removeFollow(FollowVO followVO) {
        followDAO.remove(followVO);

        AlertVO alertVO = new AlertVO();
        alertVO.setAlertUser(followVO.getFollowerNumber());
        alertVO.setUserNumber(followVO.getFollowingNumber());
        alertVO.setTypeAlert("follow");

        alertDAO.remove(alertDAO.getAlertNumber(alertVO));
    }

    @Override
    public int followingCount(FollowVO followVO) {
        return 0;
    }

    @Override
    public int followerCount(FollowVO followVO) {
        return 0;
    }

    @Override
    public int followingCheck(FollowVO followVO) {
        return followDAO.followingCheck(followVO);
    }

    @Override
    public void registerMessageRoom(MessageVO messageVO) {

    }

    @Override
    public List<MessageVO> getMessageList(MessageVO messageVO) {
        return null;
    }

    @Override
    public void registerMedal(AchievementVO achievementVO) {

    }

    @Override
    public List<AchievementVO> getMedalList(Long userNumber) {
        return null;
    }

    @Override
    public AchievementVO readMedal(Long userNumber) {
        return null;
    }


    @Override
    public List<UserDTO> userSearch(SearchDTO searchDTO) {
        return userDAO.userSearch(searchDTO);
    }

    @Override
    public int getTotal() {
        return userDAO.getTotal();
    }

    @Override
    public Long getUserChart(String date) {
        return userDAO.getUserChart(date);
    }

    @Override
    public int updateNicknameForReview(Long userNumber, String nickname) {
        return reviewDAO.updateNicknameForReview(userNumber, nickname);
    }

    @Override
    public int updateNicknameForReply(Long userNumber, String nickname) {
        return replyDAO.updateNicknameForReply(userNumber,nickname);
    }

//    *************************************
//    MEDAL 메달
//    *************************************

    /* 회원가입시 메달1 획득 */
    @Override
    public void register(UserVO userVO) {
        userDAO.register(userVO);
        // 회원가입시 얻는 메달
        achievementDAO.insertMedal(userVO.getUserNumber(), "1");
    }

    @Override
    public List<String> getMedal(Long userNumber) {
        return userDAO.selectAchievementByUserNumber(userNumber);
    }

    @Override
    public void insertMedal(Long userNumber, String medal) {
        achievementDAO.insertMedal(userNumber, medal);
    }

    @Override
    public int medal4Condition(Long userNumber) { return achievementDAO.medal4Condition(userNumber); }

    @Override
    public int medal5Condition(Long userNumber) { return achievementDAO.medal5Condition(userNumber); }

    @Override
    public int medal6Condition(Long userNumber) { return achievementDAO.medal6Condition(userNumber); }

    @Override
    public int medal7Condition(Long userNumber, String category) { return achievementDAO.medal7Condition(userNumber, category); }

    @Override
    public int medal8Condition(Long userNumber, String category) { return achievementDAO.medal8Condition(userNumber, category); }

    @Override
    public int medal9Condition(Long userNumber, String category) { return achievementDAO.medal9Condition(userNumber, category); }

    @Override
    public int medal10Condition(Long userNumber, String category) { return achievementDAO.medal10Condition(userNumber, category); }

    @Override
    public int medal11Condition(Long userNumber, String category) { return achievementDAO.medal11Condition(userNumber, category); }

    @Override
    public int medalInsertBlock(Long userNumber, String medal) { return achievementDAO.medalInsertBlock(userNumber, medal); }

    @Override
    public int medal12Condition(Long userNumber) { return achievementDAO.medal12Condition(userNumber); }

    @Override
    public int medal13Condition(Long userNumber) { return achievementDAO.medal13Condition(userNumber); }

    @Override
    public int medal14Condition(Long userNumber) { return achievementDAO.medal14Condition(userNumber); }

    @Override
    public int medal15Condition(Long userNumber) { return achievementDAO.medal15Condition(userNumber); }


}
