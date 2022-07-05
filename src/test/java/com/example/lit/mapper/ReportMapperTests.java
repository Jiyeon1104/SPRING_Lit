package com.example.lit.mapper;

import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReportDTO;
import com.example.lit.domain.vo.review.ReportVO;
import com.example.lit.mapper.review.ReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ReportMapperTests {
    @Autowired
    private ReportMapper reportMapper;

    @Test
    public void insertTest(){
        ReportVO reportVO = new ReportVO();
        reportVO.setReviewNumber(1L);
        reportVO.setUserNumber(1L);
        reportVO.setReason("TestReason");

        reportMapper.insert(reportVO);

    }


    @Test
    public void deleteTest(){
        reportMapper.delete(1L);
    }

    @Test
    public void searchReportTest(){
        SearchDTO searchDTO =  new SearchDTO();
        searchDTO.setCategory("hobby");
        searchDTO.setKeyword("aa");
        searchDTO.setType("email");
        searchDTO.setCategory("hobby");
//        searchDTO.setStartDate("2022-06-18");
//        searchDTO.setEndDate("2022-08-15");

        reportMapper.searchReport(searchDTO).stream().map(ReportDTO::toString).forEach(log::info);
    }

    @Test
    public void getTotalToday(){
        log.info(String.valueOf(reportMapper.getTotalToday()));
    }

}
