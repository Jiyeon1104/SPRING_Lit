package com.example.lit.domain.dao.user;

import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.user.UserDTO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserMapper userMapper;

    //회원 가입
    public void register(UserVO userVO){ userMapper.insert(userVO); }
    //로그인
    public UserVO login(String email, String pw){ return userMapper.login(email, pw); }
    //카카오 회원 가입
    public void kakaoLogin(UserVO userVO){ userMapper.kakaoInsert(userVO); }
    //카카오 이메일로 유저넘버 읽어오기
    public Long kakaoRead(String email){ return userMapper.kakaoRead(email); }
    //카카오 닉네임/비번 입력 (수정)
    public void kakaoUpdate(UserVO userVO){ userMapper.kakaoUpdate(userVO); }

    public boolean adminLogin(String email, String pw){ return userMapper.adminLogin(email, pw) != 0; }

    //회원 탈퇴
    public void remove(Long userNumber){ userMapper.delete(userNumber); }
    //내 정보 가져 오기
    public UserVO read(Long userNumber){ return userMapper.getUser(userNumber); }
    //내 정보 수정
    public void modify(UserVO userVO){ userMapper.update(userVO); }
    //비밀 번호 변경
    public void modifyPw(Long userNumber, String newPassword){ userMapper.updatePw(userNumber, newPassword); }
    // 대표 메달 설정
    public void modifyMedal(UserVO userVO){ userMapper.updateMedal(userVO); }

    // 관리자 유저 검색
    public List<UserDTO> userSearch(SearchDTO searchDTO){ return userMapper.userSearch(searchDTO);}
    // 유저 토탈
    public int getTotal() {return userMapper.getTotal(); }
    // 차트 정보
    public Long getUserChart(String date) { return userMapper.getUserChart(date); }
    //이메일 중복체크
    public int dbEmailCheck(String email){ return userMapper.emailCheck(email);}
    //닉네임 중복체크
    public int dbNicknameCheck(String nickname){ return userMapper.nicknameCheck(nickname);}
    //마이페이지 게시글 수 불러오기
    public int MyReviewCnt(Long userNumber){ return userMapper.mypageReviewCnt(userNumber); }
    //마이페이지 팔로워 수
    public int MyFollowerCnt(Long userNumber){ return userMapper.followerCnt(userNumber); }
    //마이페이지 팔로잉 수
    public int MyFollowingCnt(Long userNumber){ return userMapper.followingCnt(userNumber); }
    //마이페이지 팔로워 모달 정보 띄우기
    public List<UserVO> ModalFollower(Long userNumber) { return userMapper.getFollowerList(userNumber); }
    //마이페이지 팔로우 모달 정보 띄우기
    public List<UserVO> ModalFollowing(Long userNumber) { return userMapper.getFollowingList(userNumber); }
    //마이페이지 팔로우 삭제
    public void removeFollower(Long followerNumber, Long followingNumber) { userMapper.deleteFollower(followerNumber, followingNumber); }
    //회원정보수정페이지 비번 조회
    public boolean dbOldPwCheck(String password, Long userNumber) { return userMapper.oldPasswordCheck(password, userNumber) == 1; }
    //정보수정페이지 해당 유저 정보 띄워주기
    public UserVO getChangePwInfo(Long userNumber) { return userMapper.getUser(userNumber); }

//    *************************************
//    MEDAL 메달
//    *************************************
    public List<String> selectAchievementByUserNumber(Long userNumber){
        return userMapper.selectAchievementByUserNumber(userNumber);
    }
}
