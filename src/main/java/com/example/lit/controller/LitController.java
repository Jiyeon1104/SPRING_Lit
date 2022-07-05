package com.example.lit.controller;

import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.service.project.LitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/lit/*")
public class LitController {
    private final LitService litService;


    @GetMapping("/write")
    public String write(Long userNumber, Model model){
        log.info("*************************************");
        log.info("LitController : write(get)" );
        log.info("*************************************");
        log.info(userNumber + "");
        model.addAttribute("userNumber",userNumber);
        return "/project/projectWrite";
    }

    @PostMapping("/write")
    public String addWrite(ProjectVO projectVO) {
        log.info("--------------------write---------------------");
        log.info(projectVO.toString());
        log.info(projectVO.getProjectFile() + "");
        litService.register(projectVO);

        return "/main/main";
    }

    // ======= 페이지 상세보기 ========
    @GetMapping("/info")
    public String info(Long projectNumber, ProjectDTO projectDTO, HttpSession session, Model model){

        log.info(projectNumber+"###########################");
//        Long userNumber = 1L; // 테스트용
        Long userNumber = (Long)session.getAttribute("userNumber");

        projectDTO.setProjectNumber(projectNumber);
        projectDTO.setUserNumber(userNumber);
        log.info(projectNumber+"###########################");
        log.info("v프로젝트넘버 : " + projectNumber );

        projectDTO = litService.read(projectDTO);
        projectDTO.setProjectFile( litService.getImg( projectNumber ) ); // 파일 이미지
        projectDTO.setProjectNumber(projectNumber);

        session.getAttribute("userNumber");
        if(userNumber == null){
            return "/login/login";
        }
        model.addAttribute("projectNumber",projectNumber);
        model.addAttribute("projectDTO", projectDTO);

        log.info(projectNumber+"###########################");
        log.info("번호확닝 : " + projectDTO.getParticipationStatus());

        return "/project/projectInfo";
    }
}
