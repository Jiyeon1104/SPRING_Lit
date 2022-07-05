package com.example.lit.controller;

import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.project.ParticipationVO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.domain.vo.review.ReviewFileVO;
import com.example.lit.service.project.LitService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/lit/*")
public class LitRestController {
    private final LitService litService;

    @PostMapping("/upload")
    public List<ReviewFileVO> upload(MultipartFile[] uploadFiles) throws IOException {
        String uploadFolder = "C:/upload";
        ArrayList<ReviewFileVO> files = new ArrayList<>();

//        yyyy/MM/dd 경로 만들기
        File uploadPath = new File(uploadFolder, getFolder());
        if(!uploadPath.exists()){uploadPath.mkdirs();}

        for(MultipartFile file : uploadFiles){
            ReviewFileVO reviewFileVO = new ReviewFileVO();
            String uploadFileName = file.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            reviewFileVO.setName(uploadFileName);
            reviewFileVO.setUuid(uuid.toString());
            reviewFileVO.setUploadPath(getFolder());

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            log.info("--------------------------------");
            log.info("Upload File Name : " + uploadFileName);

            File saveFile = new File(uploadPath, uploadFileName);
            file.transferTo(saveFile);

            if(checkImageType(saveFile)){
                FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);
                thumbnail.close();
                reviewFileVO.setImage("1");
            }
            files.add(reviewFileVO);
        }
        return files;
    }

    @GetMapping("/display")
    public byte[] getFile(String fileName) throws IOException{
        File file = new File("C:/upload/", fileName);
        log.info(file.toString());
        return FileCopyUtils.copyToByteArray(file);
    }

    private boolean checkImageType(File file) throws IOException{
        String contentType = Files.probeContentType(file.toPath());
        return contentType.startsWith("image");
    }

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return sdf.format(date);
    }

    @PostMapping("/getMainList")
    public List<ProjectDTO> getMainList(@RequestBody ListDTO listDTO){
        log.info("***************************");
        log.info("LitRestController : getMainList(post)");
        log.info("***************************");
//        litService.getMainList(listDTO).stream().map(ProjectDTO::toString).forEach(log::info);
        listDTO.setAmount(9);
        return litService.getMainList(listDTO);
    }

    @PostMapping("/challenge")  // /lit
    public void challenge(@RequestBody ParticipationVO participationVO){
        log.info("***************************");
        log.info("challenge  : " + participationVO.getProjectNumber());
        log.info("challenge  : " + participationVO.getUserNumber());
        log.info("***************************");
        litService.join( participationVO );

    }

    @PostMapping("/getMyList/{userPageNumber}")
    public List<ProjectVO> getMyList(@PathVariable("userPageNumber")Long userPageNumber, HttpSession session, @RequestBody ListDTO listDTO){
//        Long userNumber = (Long)session.getAttribute("userNumber");
        Long userNumber = userPageNumber != null ? userPageNumber : (Long)session.getAttribute("userNumber");
        if(listDTO == null){
            listDTO = new ListDTO();
        }
        listDTO.setUserNumber(userNumber);
        return litService.getMyList(listDTO);
    }

}
