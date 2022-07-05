package com.example.lit.mapper.user;

import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.user.UserDTO;
import com.example.lit.domain.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //회원 가입
    public void insert(UserVO userVO);

    //카카오 회원 가입
    public void kakaoInsert(UserVO userVO);

    //카카오 이메일로 유저넘버 읽어오기
    public Long kakaoRead(String email);

    //카카오 닉네임/비번 입력 (수정)
    public void kakaoUpdate(UserVO userVO);

    //로그인
    public UserVO login(String email, String password);

    public int adminLogin(String email, String password);

    //회원 탈퇴
    public void delete(Long userNumber);
    //내 정보 가져 오기
    public UserVO getUser(Long userNumber);
    //내 정보 수정
    public void update(UserVO userVO);
    //비밀 번호 변경
    public void updatePw(Long userNumber, String newPassword);
    // 대표 메달 설정
    public void updateMedal(UserVO userVO);

    // 관리자 검색
    public List<UserDTO> userSearch(SearchDTO searchDTO);
    // 유저 토탈
    public int getTotal();
    // 차트 정보
    public Long getUserChart(String date);

    //이메일 중복체크
    public int emailCheck(String email);
    //닉네임 중복체크
    public int nicknameCheck(String nickname);
    //마이페이지 게시글 수 불러오기
    public int mypageReviewCnt(Long userNumber);
    //마이페이지 팔로워 수
    public int followerCnt(Long userNumber);
    //마이페이지 팔로잉 수
    public int followingCnt(Long userNumber);
    //마이페이지 팔로워 모달 정보 띄우기
    public List<UserVO> getFollowerList(Long userNumber);
    //마이페이지 팔로우 모달 정보 띄우기
    public List<UserVO> getFollowingList(Long userNumber);
    //마이페이지 팔로우 삭제
    public void deleteFollower(Long followerNumber, Long followingNumber);
    //회원정보수정페이지 비번 조회
    public int oldPasswordCheck(String password, Long userNumber);

//    *************************************
//    MEDAL 메달
//    *************************************
    
//   메달 획득
    public List<String> selectAchievementByUserNumber(Long userNumber);

}
