package com.example.lit.controller;

import com.example.lit.service.review.LitUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/litUp/*")
public class LitUpController {
    private final LitUpService litUpService;

    //테스트
    @GetMapping("/write")
    public String write(){
        log.info("******************************");
        log.info("LitUpController : write");
        log.info("******************************");
        return "/certification/certificationWrite";
    }

    //테스트
    @GetMapping("/detail")
    public String detail(){
        log.info("******************************");
        log.info("LitUpController : detail");
        log.info("******************************");
        return "certification/certificationDetail";
    }



}
