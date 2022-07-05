package com.example.lit.mapper;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.mapper.project.ProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProjectMapperTests {
    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void getListTest(){
//        projectMapper.getList(new Criteria(1, 10),"lanking").stream().map(ProjectVO::toString).forEach(log::info);
    }

    @Test
    public void insertTest(){
        ProjectVO projectVO = new ProjectVO();
        projectVO.setTitle("Testtitle");
        projectVO.setContent("TestContent");
        projectVO.setCategory("Testcate");
        projectVO.setAuthentication("TESTAuthen");
        projectVO.setStartDate("2022-06-15");
        projectVO.setEndDate("2022-06-15");

        projectMapper.insert(projectVO);

    }

    @Test
    public void getTest(){
        Long pjtNum = 11L;
        projectMapper.get(pjtNum);
    }

    @Test
    public void deleteTest(){
        Long pjtNum = 11L;
        projectMapper.delete(pjtNum);
    }

    @Test
    public void getTotalTest(){
        projectMapper.getTotal();
    }


    @Test
    public void searchProjectTest(){
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setStartDate("2022-06-01");
        searchDTO.setEndDate("2022-07-01");
        searchDTO.setKeyword("");
        searchDTO.setType("title");
        searchDTO.setCategory("");
        projectMapper.searchProject(searchDTO).stream().map(ProjectDTO::toString).forEach(log::info);
    }

    @Test
    public void getTotalByStatusTest(){
        log.info(String.valueOf(projectMapper.getTotalByStatus(1L)));
    }

    @Test
    public void getMainListTest(){
        ListDTO listDTO = new ListDTO();
        listDTO.setOrder("popular");

        projectMapper.getMainList(listDTO).stream().map(ProjectDTO::toString).forEach(log::info);
    }

//    @Test
//    public void getMyListTest(){
//        projectMapper.getMyList(1L).stream().map(ProjectVO::toString).forEach(log::info);
//    }
}
