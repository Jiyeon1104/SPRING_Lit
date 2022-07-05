package com.example.lit.domain.dao;

import com.example.lit.domain.dao.user.UserDAO;
import com.example.lit.domain.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class userDaoTests {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void insertTest(){
        UserVO userVO = new UserVO();
        userVO.setEmail("daotest");
        userVO.setName("daotestqq");
        userVO.setNickname("daotestqq");
        userVO.setPassword("1212312");
        log.info("userDAO: "+userDAO);
        userDAO.register(userVO);
    }

    @Test
    public void emailCheck(){
        String email="lje1343@naver.com";
        log.info("이메일 중복체크: " + userDAO.dbEmailCheck(email));
    }

    @Test
    public void nicknameCheck(){
        String nickname="testname";
        log.info("닉네임 중복체크: " + userDAO.dbNicknameCheck(nickname));
    }

}
