package com.example.lit.controller;

import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.domain.vo.user.UserVO;
import com.example.lit.service.User.UserService;
import com.example.lit.service.review.LitUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
public class UserRestController {
    private final UserService userService;

    //=============== 프로필 수정 ====================
    //모달 프로필 사진 바꾸기 -> 반환타입 미정
    @PostMapping("/changeImg")
    public void changeImg(MultipartFile[] uploadFiles, HttpSession session) throws IOException {
        if(uploadFiles == null){
            return;
        }
        String uploadFolder = "C:/upload";
        ArrayList<UserFileVO> files = new ArrayList<>();

        //        yyyy/MM/dd 경로 만들기
        File uploadPath = new File(uploadFolder, getFolder());
        // 쉼표로 String을 넣으면 상위경로/하위경로 로 설정된다(그냥 / 써도되는데 변수사용하면 불편해서 쉼표사용)

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }  //폴더가 없으면 해당 경로로 디렉토리 생성

        for (MultipartFile file : uploadFiles) {
            UserFileVO fileVO = new UserFileVO();
            String uploadFileName = file.getOriginalFilename();
            //            UUID
//            네트워크 상에서 각각의 객체들을 식별하기 위하여 사용되었다.
//            중복될 가능성이 거의 없다고 인정되기 때문에 많이 사용된다.
            UUID uuid = UUID.randomUUID();
            fileVO.setName(uploadFileName);
            fileVO.setUuid(uuid.toString());
            fileVO.setUploadPath(getFolder());

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            log.info("-------------------------------");
            log.info("Upload File Name : " + uploadFileName);
            log.info("Upload File size : " + file.getSize());

            File saveFile = new File(uploadPath, uploadFileName);
            file.transferTo(saveFile);

            if (checkImageType(saveFile)) {
                FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);
                thumbnail.close();
                fileVO.setImage("1");
            }
            files.add(fileVO);
            fileVO.setUserNumber((Long)session.getAttribute("userNumber"));
            userService.modifyImg(fileVO);
        }

    }

    @GetMapping("/display")
    public byte[] getFile(String fileName) throws IOException{
        File file = new File("C:/upload/", fileName);
        log.info(file.toString());
        return FileCopyUtils.copyToByteArray(file);
    }

    private boolean checkImageType(File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        return contentType.startsWith("image");
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        return sdf.format(date);
    }

    // 회원가입 - 이메일 중복검사
    @GetMapping("/dbEmailCheck/{email}")
    public boolean dbEmailCheck(@PathVariable("email") String email) {
        log.info("이메일 중복검사=============");
        return userService.dbEmailCheck(email);
    }

    // 회원가입 - 닉네임 중복검사
    @GetMapping("/dbNicknameCheck/{nickname}")
    public boolean dbNicknameCheck(@PathVariable("nickname") String nickname) {
        log.info("닉네임 중복검사=============");
        return userService.dbNicknameCheck(nickname);
    }

    // 마이페이지 - 팔로워 삭제하기
    @DeleteMapping("/removeFollower/{followerNumber}/{followingNumber}")
    public String removeFollower(@PathVariable("followerNumber") Long followerNumber, @PathVariable("followingNumber") Long followingNumber) {
        userService.removeFollower(followerNumber, followingNumber);
        return "팔로워 삭제 성공" + followerNumber;
    }

    // 마이페이지 - 비밀번호 변경부분 유저 정보 띄우기
    @PostMapping("/oldPwCheck")
    public boolean dbOldPwCheck(@RequestBody UserVO userVO) {
        log.info("이전 비밀번호 조회=============");
        log.info(userVO.getPassword() + "###################");
        log.info(userVO + "###################");
        log.info(userService.dbOldPwCheck(userVO.getPassword(), userVO.getUserNumber()) + " $$$$$$$$$$$");
        return userService.dbOldPwCheck(userVO.getPassword(), userVO.getUserNumber());
    }

    // 마이페이지 - 회원 탈퇴부분 유저 정보 띄우기
    @PostMapping("/withdrawCheck")
    public boolean withdrawCheck(@RequestBody UserVO userVO) {
        log.info(userVO.getPassword() + "###################");
        log.info(userVO + "###################");
        log.info(userService.dbOldPwCheck(userVO.getPassword(), userVO.getUserNumber()) + " $$$$$$$$$$$");
        return userService.dbOldPwCheck(userVO.getPassword(), userVO.getUserNumber());
    }

    // 마이페이지 - 회원 탈퇴 버튼
    @DeleteMapping("/withdraw/{userNumber}")
    public String withdraw(@PathVariable("userNumber") Long userNumber, HttpSession session) {
        userService.remove((Long)session.getAttribute("userNumber"));
        session.invalidate();
        return "회원 탈퇴 성공";
    }

    // 유저 프로필 사진 불러오기
    @GetMapping("/userImg")
    public UserFileVO getImg(Long userNumber) {
        return userService.getImg(userNumber);
    }

    // 마이페이지 - 비밀 번호 변경
    @PatchMapping("/modifyPw/{userNumber}/{newPassword}")
    public void modifyPw(@PathVariable("userNumber") Long userNumber, @PathVariable("newPassword") String newPassword) {
        log.info("마이페이지 비밀 번호 변경..................");
        log.info(userNumber + "");
        log.info(newPassword + "");
        userService.modifyPw(userNumber, newPassword);
    }

    // 마이페이지 - 프로필 편집 닉네임 중복 검사
    @GetMapping("/profileEditNicknameCheck/{nickname}")
    public boolean profileEditNicknameCheck(@PathVariable("nickname") String nickname) {
        log.info("프로필 편집 닉네임 중복 검사 =============");
        return userService.dbNicknameCheck(nickname);
    }

    @GetMapping("/getUser/{userNumber}")
    public UserVO getUser(@PathVariable("userNumber") Long userNumber) {
        UserVO userVO = userService.read(userNumber);
        userVO.setUserFileList(userService.getImg(userVO.getUserNumber()));

        return userVO;
    }

    @PostMapping("/follow")
    public void follow(@RequestBody FollowVO followVO ){
        log.info( "====================================================" );
        log.info( "followingNumber : " + followVO.getFollowingNumber() );
        log.info( "followerNumber : " + followVO.getFollowerNumber() );
        log.info( "====================================================" );

        userService.follow(followVO);
    }

    @GetMapping("/followCheck/{following}/{follower}")
    public int followCheck(@PathVariable("following")Long following, @PathVariable("follower")Long follower){
        FollowVO followVO = new FollowVO();
        followVO.setFollowingNumber(following);
        followVO.setFollowerNumber(follower);

        return userService.followingCheck(followVO);
    }

    @PostMapping("/deleteFollow")
    public void followDelete(@RequestBody FollowVO followVO){
        log.info( "====================================================" );
        log.info( "followingNumber : " + followVO.getFollowingNumber() );
        log.info( "followerNumber : " + followVO.getFollowerNumber() );
        log.info( "====================================================" );

        userService.removeFollow(followVO);
    }

    // 메달 2번 조건 달성 했을 때
    @GetMapping("/get2Medal/{userNumber}")
    public int get2Medal(@PathVariable("userNumber") Long userNumber){
        int medalCnt = userService.medalInsertBlock(userNumber,"2");

        return medalCnt;
    }

    // 메달 3번 조건 달성 했을 때
    @GetMapping("/get3Medal/{userNumber}")
    public int get3Medal(@PathVariable("userNumber") Long userNumber){
        int medalCnt = userService.medalInsertBlock(userNumber,"3");

        return medalCnt;
    }

    // 메달 4번 조건 달성 했을 때
    @GetMapping("/get4Medal/{userNumber}")
    public void get4Medal(@PathVariable("userNumber") Long userNumber){

        int medalCnt = userService.medalInsertBlock(userNumber,"4");

        if(medalCnt == 0) {
            userService.insertMedal(userNumber, "4");
        }

    }

    // 메달 4번 조건 달성 했을 때
    @GetMapping("/get4MedalPercent/{userNumber}")
    public int get4MedalPercent(@PathVariable("userNumber") Long userNumber){

        int medal4Cnt = userService.medal4Condition(userNumber);

        return medal4Cnt;

    }

    // 메달 5번 달성
    @GetMapping("/get5Medal/{userNumber}")
    public int get5Medal(@PathVariable("userNumber") Long userNumber){
        int reviewCnt = userService.medal5Condition(userNumber);
        int medalCnt = userService.medalInsertBlock(userNumber,"5");
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info(medalCnt + "#######################$$$$$$$$$$$$$$$$$$$#########");
        if(medalCnt == 0) {
            log.info(medalCnt + "################################");
            if (reviewCnt >= 100) {
                userService.insertMedal(userNumber, "5");
            }
        } else{
            reviewCnt = 100; // 달성률 게이지 넘어가는 것 방지
        }

        return reviewCnt;
    }

    // 메달 6번 달성
    @GetMapping("/get6Medal/{userNumber}")
    public int get6Medal(@PathVariable("userNumber") Long userNumber){
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int projectCnt = userService.medal6Condition(userNumber);
        int medalCnt = userService.medalInsertBlock(userNumber,"6");
        log.info(projectCnt + "    lits 10회 생성성공ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ");

        if(medalCnt == 0){
            if(projectCnt >= 10){
                userService.insertMedal(userNumber,"6");
            }
        } else{
            projectCnt = 10;
        }

        return projectCnt;
    }

    // 메달 7번 달성
    @GetMapping("/get7Medal/{userNumber}/{categoryName}")
    public int get7Medal(@PathVariable("userNumber") Long userNumber, @PathVariable("categoryName") String category){

        int lifeCategoryCnt = 0;

        try {
            lifeCategoryCnt = userService.medal7Condition(userNumber, category);
        } catch (Exception e){
            lifeCategoryCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"7");

        log.info(lifeCategoryCnt + "    생활####################################");

        if(medalCnt == 0) {
            if (lifeCategoryCnt >= 5) {
                userService.insertMedal(userNumber, "7");
                lifeCategoryCnt = 5;
            }
        } else {
            lifeCategoryCnt = 5; // 달성률 게이지 넘어가는 것 방지
        }
        return lifeCategoryCnt;
    }

    // 메달 8번 달성
    @GetMapping("/get8Medal/{userNumber}/{categoryName}")
    public int get8Medal(@PathVariable("userNumber") Long userNumber, @PathVariable("categoryName") String category){
        int exerciseCategoryCnt = 0;

        try {
            exerciseCategoryCnt = userService.medal7Condition(userNumber, category);
        } catch (Exception e){
            exerciseCategoryCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"8");
        log.info(exerciseCategoryCnt + "    운동####################################");

        if(medalCnt == 0) {
            if (exerciseCategoryCnt >= 5) {
                log.info("55555");
                userService.insertMedal(userNumber, "8");
                log.info("왜 안돼");
                exerciseCategoryCnt = 5;
            }
        } else {
            exerciseCategoryCnt = 5; // 달성률 게이지 넘어가는 것 방지
        }
        return exerciseCategoryCnt;
    }

    // 메달 9번 달성
    @GetMapping("/get9Medal/{userNumber}/{categoryName}")
    public int get9Medal(@PathVariable("userNumber") Long userNumber, @PathVariable("categoryName") String category){
        int heartCategoryCnt = 0;

        try {
            heartCategoryCnt = userService.medal7Condition(userNumber, category);
        } catch (Exception e){
            heartCategoryCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"9");
        log.info(heartCategoryCnt + "    정서####################################");

        if(medalCnt == 0) {
            if (heartCategoryCnt >= 5) {
                userService.insertMedal(userNumber, "9");
                heartCategoryCnt = 5;
            }
        } else {
            heartCategoryCnt = 5; // 달성률 게이지 넘어가는 것 방지
        }
        return heartCategoryCnt;
    }

    // 메달 10번 달성
    @GetMapping("/get10Medal/{userNumber}/{categoryName}")
    public int get10Medal(@PathVariable("userNumber") Long userNumber, @PathVariable("categoryName") String category){
        int hobbyCategoryCnt = 0;

        try {
            hobbyCategoryCnt = userService.medal7Condition(userNumber, category);
        } catch (Exception e){
            hobbyCategoryCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"10");
        log.info(hobbyCategoryCnt + "    취미####################################");

        if(medalCnt == 0) {
            if (hobbyCategoryCnt >= 5) {
                userService.insertMedal(userNumber, "10");
                hobbyCategoryCnt = 5;
            }
        } else {
            hobbyCategoryCnt = 5; // 달성률 게이지 넘어가는 것 방지
        }
        return hobbyCategoryCnt;
    }

    // 메달 11번 달성
    @GetMapping("/get11Medal/{userNumber}/{categoryName}")
    public int get11Medal(@PathVariable("userNumber") Long userNumber, @PathVariable("categoryName") String category){
        int artCategoryCnt = 0;

        try {
            artCategoryCnt = userService.medal7Condition(userNumber, category);
        } catch (Exception e){
            artCategoryCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"11");
        log.info(artCategoryCnt + "    예술####################################");

        if(medalCnt == 0) {
            if (artCategoryCnt >= 5) {
                userService.insertMedal(userNumber, "11");
                artCategoryCnt = 5;
            }
        } else {
            artCategoryCnt = 5; // 달성률 게이지 넘어가는 것 방지
        }
        return artCategoryCnt;
    }

    // 메달 12번 달성
    @GetMapping("/get12Medal/{userNumber}")
    public int get12Medal(@PathVariable("userNumber") Long userNumber){
        int replyCnt = 0;

        try {
            replyCnt = userService.medal12Condition(userNumber);
        } catch (Exception e){
            replyCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"12");
        log.info(replyCnt + "    댓글 1000번 달성하기####################################");
        log.info("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
        if(medalCnt == 0) {
            if (replyCnt >= 1000) {
                userService.insertMedal(userNumber, "12");
                replyCnt = 1000;
            }
        } else {
            replyCnt = 1000; // 달성률 게이지 넘어가는 것 방지
        }
        return replyCnt;
    }

    // 메달 13번 달성
    @GetMapping("/get13Medal/{userNumber}")
    public int get13Medal(@PathVariable("userNumber") Long userNumber){
        int likeCnt = 0;

        try {
            likeCnt = userService.medal13Condition(userNumber);
        } catch (Exception e){
            likeCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"13");
        log.info(likeCnt + "    좋아요 1000번 달성하기####################################");
        log.info("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
        if(medalCnt == 0) {
            if (likeCnt >= 1000) {
                userService.insertMedal(userNumber, "13");
                likeCnt = 1000;
            }
        } else {
            likeCnt = 1000; // 달성률 게이지 넘어가는 것 방지
        }
        return likeCnt;
    }

    // 메달 14번 달성
    @GetMapping("/get14Medal/{userNumber}")
    public int get14Medal(@PathVariable("userNumber") Long userNumber){
        int followingCnt = 0;

        try {
            followingCnt = userService.medal14Condition(userNumber);
        } catch (Exception e){
            followingCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"14");
        log.info(followingCnt + "    팔로잉 100명이상 만들기####################################");
        log.info("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
        if(medalCnt == 0) {
            if (followingCnt >= 100) {
                userService.insertMedal(userNumber, "14");
                followingCnt = 100;
            }
        } else {
            followingCnt = 100; // 달성률 게이지 넘어가는 것 방지
        }
        return followingCnt;
    }

    // 메달 15번 달성
    @GetMapping("/get15Medal/{userNumber}")
    public int get15Medal(@PathVariable("userNumber") Long userNumber){
        int follwerCnt = 0;

        try {
            follwerCnt = userService.medal15Condition(userNumber);
        } catch (Exception e){
            follwerCnt = 0;
        }
        int medalCnt = userService.medalInsertBlock(userNumber,"15");
        log.info(follwerCnt + "    팔로워 100만명이상 만들기####################################");
        log.info("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
        if(medalCnt == 0) {
            if (follwerCnt >= 1000000) {
                userService.insertMedal(userNumber, "15");
                follwerCnt = 1000000;
            }
        } else {
            follwerCnt = 1000000; // 달성률 게이지 넘어가는 것 방지
        }
        return follwerCnt;
    }

    @GetMapping("/profileImageDelete/{userNumber}")
    public void profileImageDelete(@PathVariable("userNumber") Long userNumber){
        log.info(userNumber + "");
        userService.removeImg(userNumber);
    }
}
