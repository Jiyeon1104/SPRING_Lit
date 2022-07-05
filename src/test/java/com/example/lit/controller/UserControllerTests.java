package com.example.lit.controller;

import com.example.lit.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest
public class UserControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void registerTest() throws Exception{
        log.info("회원가입" + mockMvc.perform(MockMvcRequestBuilders.post("/user/join")
            .param("email", "udh1343@naver.com")
            .param("name", "이지똥")
            .param("nickname","wer_test")
            .param("password","9999!")
        ).andReturn().getFlashMap());
    }



}
