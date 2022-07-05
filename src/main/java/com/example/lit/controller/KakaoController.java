package com.example.lit.controller;

import com.example.lit.service.KakaoService;
import com.example.lit.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.lit.domain.vo.user.UserVO;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;


    @GetMapping("/login")
    public RedirectView kakaoCallback(@RequestParam String code, HttpSession session, Model model) throws Exception {
        log.info(code);
        // 카카오 토큰 생성
        String token = kakaoService.getKaKaoAccessToken(code);
        session.setAttribute("token", token);

        // 카카오에서 얻어와야할 정보들
        HashMap<String, Object> userInfo = kakaoService.getKakaoInfo(token);
        log.info("nickname" + userInfo.get("nickname"));
        log.info("email" + userInfo.get("email"));

        // 세션 심어줌
        session.setAttribute("name", userInfo.get("nickname"));
        session.setAttribute("email", userInfo.get("email"));

        // 유저 객체 생성 -> 카카오API를 통해 얻어온 정보들을 셋팅해준다.
        UserVO userVO = new UserVO();
        userVO.setEmail((String)userInfo.get("email"));
        userVO.setName((String)userInfo.get("nickname"));

        // 카카오 회원가입/로그인
        UserVO tempUserVO = userService.kakaoLogin(userVO);
        // 받아온 userNumber 또한 세션 심어줌
        session.setAttribute("userNumber",tempUserVO.getUserNumber());
        log.info("---------------------------------");
        log.info("kakaoController"+tempUserVO.getUserNumber());
        log.info("---------------------------------");

        if(tempUserVO.getNickname()==null){
            // 닉네임이 없을경우(회원가입이 안되어있는 경우) -> kakaoJoin 컨트롤러 태워줌
            return new RedirectView("/user/kakaoJoin");
        }else {
            // 회원가입이 되어있는 경우 -> main 컨트롤러 태워줌
            return new RedirectView("/main");
        }
    }

    @GetMapping("/logout")
    public RedirectView kakaoLogout(HttpSession session){
        log.info("logout");
        kakaoService.logoutKakao((String)session.getAttribute("token"));
        session.invalidate();

        return new RedirectView("/main");
    }
}