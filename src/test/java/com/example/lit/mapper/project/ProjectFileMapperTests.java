package com.example.lit.mapper.project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ProjectFileMapperTests {
    @Autowired
    private ProjectFileMapper projectFileMapper;

    @Test
    public void getImgTest(){
        log.info("*************************************");
        log.info("ProjectFileMapperTests : getImgTest");
        log.info("*************************************");
        log.info(projectFileMapper.getImg(160L).toString());
    }

}