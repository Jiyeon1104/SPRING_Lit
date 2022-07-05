package com.example.lit.controller;

import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.review.ReportDTO;
import com.example.lit.domain.vo.review.ReviewDTO;
import com.example.lit.domain.vo.review.ReviewVO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.user.UserDTO;
import com.example.lit.service.User.UserService;
import com.example.lit.service.project.LitService;
import com.example.lit.service.review.LitUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminRestController {
    private final UserService userService;
    private final LitService litService;
    private final LitUpService litUpService;

    //회원 검색
    @PostMapping("/searchUser/{page}")
    public List<UserDTO> searchUser(@RequestBody SearchDTO searchDTO, @PathVariable("page") int pageNum){
        log.info("***************************");
        log.info("AdminRestController : searchUser(post)");
        log.info("***************************");
        searchDTO.setPageNum(pageNum);
        return userService.userSearch(searchDTO);
    }

    //회원 삭제
    @DeleteMapping("/user/{uno}")
    public void deleteUser(@PathVariable("uno") List<Long> list){
        log.info("***************************");
        log.info("AdminRestController : deleteUser(delete)");
        log.info("***************************");
        //삭제
        list.stream().forEach( i -> userService.remove(i) );
    }

    //프로젝트 검색
    @PostMapping("/project/search/{page}")
    public List<ProjectDTO> searchWaitingProject(@RequestBody SearchDTO searchDTO, @PathVariable("page") int pageNum){
        log.info("***************************");
        log.info("AdminRestController : searchWaitingProject(post)");
        log.info("***************************");

//        litService.searchProject(searchDTO).stream().forEach(ProjectDTO::toString);

        searchDTO.setPageNum(pageNum);
        return litService.searchProject(searchDTO);
    }

    //프로젝트 삭제 (대기중, 승인된 둘다 같이 사용)
    @DeleteMapping("/project/{param}")
    public void deleteWaitingProject(@PathVariable("param") List<Long> param){
        log.info("***************************");
        log.info("AdminRestController : deleteWaitingProject(delete)");
        log.info("***************************");

        //삭제
        param.stream().forEach( i -> litService.remove(i) );
    }

    //프로젝트 승인(상태 변경)
    @GetMapping("/project/{param}/{status}")
    public void changeStatus(@PathVariable("param")List<Long> param, @PathVariable("status")Long status){
        log.info("***************************");
        log.info("AdminRestController : changeStatus(get)");
        log.info("***************************");

        param.stream().forEach( i -> litService.changeStatus(i, status));
    }

    //프로젝트 미리보기
    @GetMapping("/waitingProject/preview/{pno}")
    public ProjectVO viewWaitingProject(){
        log.info("***************************");
        log.info("AdminRestController : viewWaitingProject(get)");
        log.info("***************************");

        return null;
    }

    //인증글 검색
    @PostMapping("/review/search/{page}")
    public List<ReviewDTO> searchReview(@RequestBody SearchDTO searchDTO, @PathVariable("page") int pageNum){
        log.info("***************************");
        log.info("AdminRestController : searchReview(post)");
        log.info("***************************");

//        litUpService.searchReview(searchDTO).stream().map(ReviewDTO::toString).forEach(log::info);
        searchDTO.setPageNum(pageNum);
        return litUpService.searchReview(searchDTO);
    }

    //인증글 삭제
    @DeleteMapping("/review/{param}")
    public void deleteReview(@PathVariable("param") List<Long> param){
        log.info("***************************");
        log.info("AdminRestController : deleteReview(delete)");
        log.info("***************************");

        param.stream().forEach(i -> litUpService.remove(i));
    }

    //인증글 보기
    @GetMapping("/review/view/{rno}")
    public ReviewVO viewReview(){
        log.info("***************************");
        log.info("AdminRestController : viewReview(get)");
        log.info("***************************");

        return null;
    }



    //신고 검색
    @PostMapping("/report/search/{page}")
    public List<ReportDTO> searchReport(@RequestBody SearchDTO searchDTO, @PathVariable("page") int pageNum){
        log.info("***************************");
        log.info("AdminRestController : searchReport(post)");
        log.info("***************************");

//        litUpService.searchReport(searchDTO).stream().map(ReportDTO::toString).forEach(log::info);
        searchDTO.setPageNum(pageNum);
        return litUpService.searchReport(searchDTO);
    }

    //신고된 글 삭제
    @DeleteMapping("/report/{param}")
    public void deleteReport(@PathVariable("param")List<Long> param){
        log.info("***************************");
        log.info("AdminRestController : deleteReport(delete)");
        log.info("***************************");

//        param.stream().distinct().map(String::valueOf).forEach(log::info);
        //리스트 중복제거 후 삭제(이미 삭제된 게시물을 삭제하려고 할 수 있으므로)
        param.stream().distinct().forEach( i -> litUpService.remove(i) );

    }

    //신고 취소
    @DeleteMapping("/report/cancel/{param}")
    public void cancelReport(@PathVariable("param")List<Long> param){
        log.info("***************************");
        log.info("AdminRestController : cancelReport(delete)");
        log.info("***************************");

        param.stream().forEach( i -> litUpService.removeReport(i));
    }

    //신고된 글 보기
    @GetMapping("/report/view/{rno}")
    public Long viewReport(){
        log.info("***************************");
        log.info("AdminRestController : viewReport(get)");
        log.info("***************************");
        //검색 결과의 보기 버튼 누르면 해당 게시물이 새 창으로 떠야함(ajax 사용)

        return null;
    }


    //차트 데이터

    @GetMapping("/user/chart")
    public List<Long> userChart(){
        log.info("***************************");
        log.info("AdminRestController : userChart(get)");
        log.info("***************************");
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        List<Long> list = new ArrayList<>();

        for(int i=0; i<7; i++){
            list.add(userService.getUserChart(sdf.format(today.getTime())));
            today.add(Calendar.DATE, -1);
        }

        Collections.reverse(list);
//        list.stream().forEach(System.out::println);

        return list;
    }

    @GetMapping("/review/chart")
    public List<Long> reviewChart(){
        log.info("***************************");
        log.info("AdminRestController : reviewChart(get)");
        log.info("***************************");
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        List<Long> list = new ArrayList<>();

        for(int i=0; i<7; i++){
            list.add(litUpService.getReviewChart(sdf.format(today.getTime())));
            today.add(Calendar.DATE, -1);
        }

        Collections.reverse(list);
        return list;
    }

    @GetMapping("/report/chart")
    public List<Long> reportChart(){
        log.info("***************************");
        log.info("AdminRestController : reportChart(get)");
        log.info("***************************");
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        List<Long> list = new ArrayList<>();

        for(int i=0; i<7; i++){
            list.add(litUpService.getReportChart(sdf.format(today.getTime())));
            today.add(Calendar.DATE, -1);
        }

        Collections.reverse(list);
        return list;
    }

}































