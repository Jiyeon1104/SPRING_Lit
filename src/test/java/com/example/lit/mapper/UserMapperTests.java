package com.example.lit.mapper;


import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.user.AlertVO;
import com.example.lit.domain.vo.user.UserDTO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.mapper.user.AlertMapper;
import com.example.lit.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AlertMapper alertMapper;

    @Test
    public void insertTest(){
        UserVO userVO = new UserVO();
        userVO.setEmail("abc");
        userVO.setName("asd");
        userVO.setNickname("azx");
        userVO.setPassword("1234");
        log.info("userVO: "+userVO);
        userMapper.insert(userVO);
    }

    @Test
    public void emailCheck(){
        String email="lje1343@naver.com";
        log.info("이메일 중복체크: " + userMapper.emailCheck(email));
    }

    @Test
    public void nicknameCheck(){
        String nickname="testname";
        log.info("닉네임 중복체크: " + userMapper.nicknameCheck(nickname));
    }

    @Test
    public void adminLoginTest(){
        String email="test12";
        String password = "1234";
        log.info(userMapper.adminLogin(email,password)+"");
    }

    @Test
    public void deleteTest(){
        Long userNumber = 621L;
        userMapper.delete(userNumber);
    }

    @Test
    public void getUserTest(){
        Long userNumber = 2L;
        userMapper.getUser(userNumber);
        log.info(userMapper.getUser(userNumber).toString());
    }

    @Test
    public void updateTest(){
        UserVO userVO = new UserVO();
        userVO.setName("updateTest");
        userVO.setNickname("updateTest");
        userVO.setEmail("updateTest");
        userVO.setContent("updateTest");
        userVO.setUserNumber(40L);

        userMapper.update(userVO);
    }

//    @Test
//    public void updatePwTest(){
//        UserVO userVO = new UserVO();
//        userVO.setUserNumber(622L);
//        userVO.setPassword("2345");
//        String newPassword="1234";
//
//        userMapper.updatePw(userVO,newPassword);
//    }

    @Test
    public void updateMedalTest(){
        UserVO userVO = new UserVO();
        userVO.setUserNumber(622L);
        userVO.setAchievementNumber(1L);

        userMapper.updateMedal(userVO);
    }

    @Test
    public void adminSelectTest(){
//        SELECT USER_NUMBER, EMAIL, NAME, NICKNAME, PASSWORD, CONTENT, KAKAO, REGISTER_DATE, ACHIEVEMENT_NUMBER
//        FROM TBL_USER
//        WHERE EMAIL LIKE '%test%'
//        AND KAKAO IS NULL
//        AND REGISTER_DATE > '2022-06-14' AND REGISTER_DATE < '2022-06-16';
        SearchDTO search = new SearchDTO();
        search.setKeyword("");
        search.setType("email");
        search.setKakao("0"); // kakao를 0(all), 1(null), 2(not null) 로 받아서 검사하기
        search.setDesc(false);
        search.setOrder("user_number");
        log.info(search.getAmount() + "aaaaa");
        log.info(search.getPageNum() + "aaaaa");
        userMapper.userSearch(search).stream().map(UserDTO::toString).forEach(log::info);
    }

    @Test
    public void getTotalTest(){
        log.info(String.valueOf(userMapper.getTotal()));
    }

    @Test
    public void getUserChartTest(){
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        List<Long> list = new ArrayList<>();

        for(int i=0; i<7; i++){
            list.add(userMapper.getUserChart(sdf.format(today.getTime())));
            today.add(Calendar.DATE, -1);
        }

        log.info(list.toString());

    }

    @Test
    public void alertInsertTest() {
        AlertVO alertVO = new AlertVO();
        alertVO.setAlertUser(1L);
        alertVO.setUserNumber(3L);
        alertVO.setTypeAlert("like");
        alertMapper.insert(alertVO);
    }

    @Test
    public void listTest(){
        log.info(alertMapper.alertList(1L).toString());
    }


    @Test
    public void alertRemoveTest() {
        alertMapper.remove(3);
    }
}
