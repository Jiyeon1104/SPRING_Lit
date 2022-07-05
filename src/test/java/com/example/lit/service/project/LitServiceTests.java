package com.example.lit.service.project;

import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.project.ParticipationVO;
import com.example.lit.domain.vo.project.ProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LitServiceTests {
    @Autowired
    private LitService litService;

    @Test
    public void getImgTest(){
        log.info("*************************************");
        log.info("LitServiceTests : getImgTest");
        log.info("*************************************");
        log.info(litService.getImg(160L).toString());
    }

    @Test
    public void getMainListTest(){
        log.info("*************************************");
        log.info("LitServiceTests : getMainListTest");
        log.info("*************************************");
        ListDTO listDTO = new ListDTO();
        listDTO.setOrder("new");
//        litService.getMainList(listDTO).stream().map(ProjectDTO::toString).forEach(log::info);
    }

    @Test
    public void joinTest(){
        log.info("*************************************");
        log.info("LitServiceTests : joinTest");
        log.info("*************************************");
        ParticipationVO participationVO = new ParticipationVO();
        participationVO.setStatus(1L);
        participationVO.setProjectNumber(11L);
        participationVO.setUserNumber(6L);
        litService.join(participationVO);

    }
}