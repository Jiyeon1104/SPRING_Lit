package com.example.lit.controller;

import com.example.lit.domain.vo.AdminSession;
import com.example.lit.service.User.UserService;
import com.example.lit.service.project.LitService;
import com.example.lit.service.review.LitUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final LitService litService;
    private final LitUpService litUpService;

    @GetMapping("/login")
    public String login(){
        log.info("***************************");
        log.info("AdminController : login(get)");
        log.info("***************************");
        return "/admin/login";
    }

    @PostMapping("/login")
    public RedirectView adminLogin(@RequestParam Map<String, String> info, RedirectAttributes rttr, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : login(post)");
        log.info("***************************");

        if(info.get("email") == null || info.get("password") == null){
            return new RedirectView("/admin/login");
        }
        HttpSession session = req.getSession();

        AdminSession.setSession(session);


        String result = userService.adminLogin(info.get("email"), info.get("password")) ? "/admin/user" : "/admin/login";
        //관리자 페이지는 단순히 email pw만 일치해서 들어갈 수 있으면 안됨 => 수정해야함
        return new RedirectView(result);
    }


    @GetMapping("/user")
    public String user(Model model, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : user(get)");
        log.info("***************************");
        model.addAttribute("total", userService.getTotal());
        String result = AdminSession.checkSession(req.getSession(), "/admin/user-management");

        return result;
    }

    @GetMapping("/approvedProject")
    public String approvedProject(Model model, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : approvedProject(get)");
        log.info("***************************");
        model.addAttribute("total", litService.getTotalByStatus(1L));

        String result = AdminSession.checkSession(req.getSession(), "/admin/approved-project-management");
        return result;
    }

    @GetMapping("/report")
    public String  report(Model model, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : report(get)");
        log.info("***************************");
        model.addAttribute("total", litUpService.getTotalTodayReport());

        String result = AdminSession.checkSession(req.getSession(), "/admin/report-management");
        return result;
    }

    @GetMapping("/waitingProject")
    public String waitingProject(Model model, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : waitingProject(get)");
        log.info("***************************");
        model.addAttribute("total", litService.getTotalByStatus(0L));

        String result = AdminSession.checkSession(req.getSession(), "/admin/waiting-project-management");
        return result;
    }

    @GetMapping("/review")
    public String reviewManagement(Model model, HttpServletRequest req){
        log.info("***************************");
        log.info("AdminController : review(get)");
        log.info("***************************");

        model.addAttribute("total", litUpService.getTotalTodayReview());
        String result = AdminSession.checkSession(req.getSession(), "/admin/review-management");
        return result;
    }


}
