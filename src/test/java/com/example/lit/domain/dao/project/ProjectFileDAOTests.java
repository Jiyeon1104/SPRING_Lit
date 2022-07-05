package com.example.lit.domain.dao.project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class ProjectFileDAOTests {
    @Autowired
    private ProjectFileDAO projectFileDAO;

    @Test
    public void getImgTest(){
        log.info("*************************************");
        log.info("ProjectFileDAOTests : getImgTest");
        log.info("*************************************");
        log.info(projectFileDAO.getImg(160L).toString());
    }

}