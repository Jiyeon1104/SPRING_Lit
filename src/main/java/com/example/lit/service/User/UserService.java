package com.example.lit.service.User;

import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.user.*;
import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.messsage.MessageVO;
import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.domain.vo.user.achievement.AchievementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    //회원 가입
    public void register(UserVO userVO);
    //로그인
    public UserVO login(String email, String pw);
    //카카오 회원 가입
    public UserVO kakaoLogin(UserVO userVO);
    //카카오 이메일로 유저넘버 읽어오기
    public Long kakaoRead(String email);
    //카카오 닉네임/비번 입력 (수정)
    public boolean kakaoUpdate(UserVO userVO);

    public boolean adminLogin(String email, String password);

    //회원 탈퇴
    public void remove(Long userNumber);
    //내 정보 가져 오기
    public UserVO read(Long userNumber);
    //내 정보 수정
    public void modify(UserVO userVO);
    //비밀 번호 변경
    public void modifyPw(Long userNumber, String newPassword);
    // 대표 메달 설정
    public void modifyMedal(UserVO userVO);
    //이메일 중복체크
    public boolean dbEmailCheck(String email);
    //닉네임 중복체크
    public boolean dbNicknameCheck(String nickname);
    //마이페이지 게시글 수 불러오기
    public int MyReviewCnt(Long userNumber);
    //마이페이지 팔로워 수
    public int MyFollowerCnt(Long userNumber);
    //마이페이지 팔로잉 수
    public int MyFollowingCnt(Long userNumber);
    //마이페이지 팔로워 모달 정보 띄우기
    public List<UserVO> ModalFollower(Long userNumber);
    //마이페이지 팔로우 모달 정보 띄우기
    public List<UserVO> ModalFollowing(Long userNumber);
    //마이페이지 팔로우 삭제
    public void removeFollower(Long followerNumber, Long followingNumber);
    //회원정보수정페이지 비번 조회
    public boolean dbOldPwCheck(String password, Long userNumber);
    //정보수정페이지 해당 유저 정보 띄워주기
    public UserVO getChangePwInfo(Long userNumber);

    //유저 파일 업로드
    public void registerImg(UserFileVO userFileVO);
    //유저 파일 지우기
    public void removeImg(Long userNumber);
    //유저 파일 수정
    public void modifyImg(UserFileVO userFileVO);
    // 사진
    public UserFileVO getImg(Long userNumber);
    // DB에 없는 이미지 삭제
    public List<UserFileVO> getOldFiles();


    // 팔로우
    public void follow(FollowVO followVO);
    // 팔로우 취소
    public void removeFollow(FollowVO followVO);
    // 팔로잉 카운트 (내가 한 사람)
    public int followingCount(FollowVO followVO);
    // 팔로워 카운트 (다른 사람)
    public int followerCount(FollowVO followVO);
    // 팔로잉 체크
    public int followingCheck(FollowVO followVO);


    //메세지 룸 생성
    public void registerMessageRoom(MessageVO messageVO);
    //메세지 리스트 띄우기
    public List<MessageVO> getMessageList(MessageVO messageVO);


    //메달 추가
    public void registerMedal(AchievementVO achievementVO);
    //메달 전체 해금 유무 (?)
    public List<AchievementVO> getMedalList(Long userNumber);
    //메달 하나 보기
    public AchievementVO readMedal(Long userNumber);


    // 관리자 유저 검색
    public List<UserDTO> userSearch(SearchDTO searchDTO);
    // 유저 토탈
    public int getTotal();
    // 차트 정보
    public Long getUserChart(String date);

    // 내정보 변경시 닉네임 바꾸기
    public int updateNicknameForReview(Long userNumber, String nickname);
    public int updateNicknameForReply(Long userNumber, String nickname);

//    *************************************
//    MEDAL 메달
//    *************************************
    public List<String> getMedal(Long userNumber);

    //메달 획득
    public void insertMedal(@Param("userNumber") Long userNumber, @Param("medal") String medal);

    //4번째 메달 조건 - 메달 10개 모으기
    public int medal4Condition(Long userNumber);

    // 5번째 메달 조건 - 전체 카테고리 litUp 100회 성공
    public int medal5Condition(Long userNumber);

    // 6번째 메달 조건 - lits 10번 생성하기 성공
    public int medal6Condition(Long userNumber);

    // 7번째 메달 조건 - 생활 카테고리 5회 성공하기
    public int medal7Condition(Long userNumber, String category);

    // 8번째 메달 조건 - 운동 카테고리 5회 성공하기
    public int medal8Condition(Long userNumber, String category);

    // 9번째 메달 조건 - 정서 카테고리 5회 성공하기
    public int medal9Condition(Long userNumber, String category);

    // 10번째 메달 조건 - 취미 카테고리 5회 성공하기
    public int medal10Condition(Long userNumber, String category);

    // 11번째 메달 조건 - 예술 카테고리 5회 성공하기
    public int medal11Condition(Long userNumber, String category);

    // 메달 1번이상 인서트 되는 것 막아주기
    public int medalInsertBlock(Long userNumber, String medal);

    // 12번째 메달 조건 - 댓글 1000번 달성하기
    public int medal12Condition(Long userNumber);

    // 13번째 메달 조건 - 좋아요 1000번 달성하기
    public int medal13Condition(Long userNumber);

    // 14번째 메달 조건 - 팔로잉 100명이상 만들기
    public int medal14Condition(Long userNumber);

    // 15번째 메달 조건 - 팔로워 100만명이상 만들기
    public int medal15Condition(Long userNumber);

}
