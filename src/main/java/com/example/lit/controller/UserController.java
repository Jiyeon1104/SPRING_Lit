package com.example.lit.controller;

import com.example.lit.domain.dao.user.UserFileDAO;
import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.domain.vo.user.achievement.AchievementVO;
import com.example.lit.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
public class UserController {
    private final UserService userService;

    //이동
    @GetMapping("/changePw")
    public String goChangePwPage(HttpSession session, Model model){
        log.info("******************************");
        log.info("changeInfoController : changePw");
        log.info("******************************");
        Long userNumber = (Long)session.getAttribute("userNumber");
        if(userNumber == null){
            return goLoginPage();
        }

        UserVO userVO = userService.getChangePwInfo(userNumber);
        UserFileVO userFileVO = null;
        if(userService.getImg(userNumber) == null){
            userFileVO = new UserFileVO();
            userFileVO.setUserNumber(userNumber);
            userFileVO.setName("");
            userFileVO.setUploadPath("");
            userFileVO.setUuid("");
            userVO.setUserFileList(userFileVO);
        }else{
            userVO.setUserFileList(userService.getImg(userNumber));
        }
        model.addAttribute("userFileList", userVO.getUserFileList());
        model.addAttribute("userNumber", userNumber);
        model.addAttribute("nickName", userVO.getNickname());
        log.info(userVO.getUserNumber() + "########################");
        log.info(userVO.getNickname() + "########################");
        return "/changeinfo/changePw";
    }

    //이동
    @GetMapping("/editInfo")
    public String goEditInfoPage(HttpSession session, Model model){
        log.info("******************************");
        log.info("changeInfoController : editInfo");
        log.info("******************************");
        Long userNumber = (Long)session.getAttribute("userNumber");
        if(userNumber == null){
            return goLoginPage();
        }

        UserVO userVO = userService.read(userNumber);

        if(userService.getImg(userNumber) == null){
            UserFileVO userFileVO = new UserFileVO();
            userFileVO.setUserNumber(userNumber);
            userFileVO.setName("");
            userFileVO.setUploadPath("");
            userFileVO.setUuid("");
            userVO.setUserFileList(userFileVO);
        }else{
            userVO.setUserFileList(userService.getImg(userNumber));
        }
        model.addAttribute("userVO", userVO);

        return "/changeinfo/editInfo";
    }

    //이동
    @GetMapping("/withdraw")
    public String goWithdrawPage(HttpSession session, Model model){
        log.info("******************************");
        log.info("changeInfoController : withdraw");
        log.info("******************************");
        Long userNumber = (Long)session.getAttribute("userNumber");
        if(userNumber == null){
            return goLoginPage();
        }

        UserVO userVO = userService.getChangePwInfo(userNumber);
        UserFileVO userFileVO = null;
        if(userService.getImg(userNumber) == null){
            userFileVO = new UserFileVO();
            userFileVO.setUserNumber(userNumber);
            userFileVO.setName("");
            userFileVO.setUploadPath("");
            userFileVO.setUuid("");
            userVO.setUserFileList(userFileVO);
        }else{
            userVO.setUserFileList(userService.getImg(userNumber));
        }
        model.addAttribute("userFileList", userVO.getUserFileList());
        model.addAttribute("userNumber", userNumber);
        model.addAttribute("nickName", userVO.getNickname());
        return "/changeinfo/withdraw";
    }

    //이동
    @GetMapping("/login")
    public String goLoginPage() {
        log.info("******************************");
        log.info("LoginController : login");
        log.info("******************************");
        return "/login/login";
    }

    //이동
    @GetMapping("/join")
    public String goJoinPage() {
        log.info("******************************");
        log.info("LoginController : join");
        log.info("******************************");
        return "/login/join";
    }

    //이동
    @GetMapping("/kakaoJoin")
    public String goJoinkakaoPage(HttpSession session, Model model) {
        log.info("******************************");
        log.info("LoginController : kakaoJoin");
        log.info("******************************");

        // kakaoCallback 컨트롤러에서 저장해준 세션을 가져와서 userNumber를 읽음
        Long userNumber = Long.parseLong(session.getAttribute("userNumber").toString());

        // 유저번호로 해당 유저의 정보를 모델에 저장함
        model.addAttribute("userVO", userService.read(userNumber));

        return "/login/kakaoJoin";
    }

    //정보 수정
    @PostMapping("/changeInfo")
    public String changeInfo(UserVO userVO, HttpSession session, Model model){
        Long userNumber = (Long)session.getAttribute("userNumber");
        if(userNumber == null){
            return goLoginPage();
        }
        userVO.setUserNumber((Long)session.getAttribute("userNumber"));

        userService.modify(userVO);
        userService.updateNicknameForReview(userVO.getUserNumber(),userVO.getNickname());
        userService.updateNicknameForReply(userVO.getUserNumber(),userVO.getNickname());

        return mypage(null ,model, session);
    }


    //가입
    @PostMapping("/join")
    public RedirectView register(UserVO userVO, RedirectAttributes rttr) {
        userService.register(userVO);

        return new RedirectView("/user/login");
    }

    //카카오 가입
    @PostMapping("/kakaoJoin")
    public RedirectView kakaoLogin(HttpSession session, UserVO userVO, RedirectAttributes rttr) {
        Long number = Long.parseLong(session.getAttribute("userNumber").toString());
        log.info(number.toString());

        // insert같지만 update 되야함
        userService.kakaoUpdate(userVO);
        return new RedirectView("/main");
    }

    //로그인
    @PostMapping("/loginOk")
    public RedirectView login(String email, String password, Model model, HttpServletRequest req, RedirectAttributes rttr) {
        try {
            HttpSession session = req.getSession();
            UserVO userVO = userService.login(email, password);
            /* ##### 유저 프로필 사진으로 수정 필요 #####*/
            model.addAttribute("info", userVO.getNickname() + "님 환영합니다.");
            session.setAttribute("userNumber", userVO.getUserNumber());

        } catch (Exception e) {
            rttr.addFlashAttribute("loginStatus", false);
            return new RedirectView("/user/login");
        }
        return new RedirectView("/main");
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        log.info("로그아웃 컨트롤러 들어옴");
        req.getSession().invalidate();
        req.getSession(true);
        return "/main/main";
    }

    //카톡 가입은 api활용하기(카카오 id값(숫자) 가져오기)

    //마이페이지 데이터 가져와서 들어가기
    @GetMapping("/mypage")
    public String mypage( Long userPageNumber, Model model, HttpSession session) {
        log.info( "마이페이지 컨트롤러 =============================");
        Long userNumber = (Long) session.getAttribute("userNumber");
        if (userNumber == null) {
            return goLoginPage();
        }

        if(userPageNumber == null){
            userPageNumber = userNumber;
        }

        FollowVO followVO = new FollowVO();
        followVO.setFollowingNumber(userNumber);
        followVO.setFollowerNumber(userPageNumber);

//        System.out.println("==============================================");
//        System.out.println("userNumber : " + userNumber);
//        System.out.println("pageUserNumber : " + userPageNumber);
//        System.out.println("==============================================");


        UserVO userVO = userService.read(userPageNumber);
        List<UserVO> followerVO = userService.ModalFollower(userPageNumber);
        List<UserVO> followingVO = userService.ModalFollowing(userPageNumber);
        log.info("==Runarell======================================================");
//        log.info("uuit : " + followerVO.get(0).getUserFileList().getUuid() );
//        log.info("name : " + followerVO.get(0).getUserFileList().getName() );
//        log.info("uploadPath : " + followerVO.get(0).getUserFileList().getUploadPath() );
        log.info("========================================================");

        UserFileVO userFileVO = null;
        if(userService.getImg(userPageNumber) == null){
            userFileVO = new UserFileVO();
            userFileVO.setUserNumber(userPageNumber);
            userFileVO.setName("");
            userFileVO.setUploadPath("");
            userFileVO.setUuid("");
            userVO.setUserFileList(userFileVO);
        }else{
            userVO.setUserFileList(userService.getImg(userPageNumber));
            log.info("***********************************************************");
            log.info("In");
            log.info("***********************************************************");
        }

        model.addAttribute("userFileList", userVO.getUserFileList());
        model.addAttribute("followerCnt", userService.MyFollowerCnt(userPageNumber));
        model.addAttribute("followingCnt", userService.MyFollowingCnt(userPageNumber));
        model.addAttribute("reviewCnt", userService.MyReviewCnt(userPageNumber));

        model.addAttribute("followingCheck", userService.followingCheck(followVO));

        model.addAttribute("nickname", userVO.getNickname());
        model.addAttribute("content", userVO.getContent());
        model.addAttribute("userNumber", userNumber);
        model.addAttribute("userPageNumber", userPageNumber); // 이동된 페이지 변호

        model.addAttribute("modalFollower", followerVO);
        log.info("###################  follower모달정보     " + followerVO.toString());
        model.addAttribute("modalFollowing", followingVO);
        log.info("###################  following모달정보     " + followingVO.toString());

        return "/mypage/mypage";
    }

    @PostMapping("/updateEditInfo")
    public String updateEditInfo(UserVO userVO, Model model, HttpSession session) {
        userVO.setUserNumber(2L);
        userService.modify(userVO);
        return goEditInfoPage(session, model);
    }

    //    *************************************
//    MEDAL 메달
//    ************************************* 
    @GetMapping("/getMedal/{userNumber}")
    @ResponseBody
    public List<String> getMedal(@PathVariable("userNumber") Long userNumber) {
        log.info("getMedal................ : " + userNumber);
        return userService.getMedal(userNumber);
    }

}
