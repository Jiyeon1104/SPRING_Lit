package com.example.lit.domain.dao;

import com.example.lit.domain.dao.project.ProjectDAO;
import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.project.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProjectDAOTast {
    @Autowired
    private ProjectDAO projectDAO;


    @Test
    public void getListTest(){
//        projectDAO.getList(new Criteria(1, 10),"newproject").stream().map(ProjectVO::toString).forEach(log::info);
    }
}
