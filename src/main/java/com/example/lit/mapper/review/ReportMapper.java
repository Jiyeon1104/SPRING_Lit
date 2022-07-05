package com.example.lit.mapper.review;

import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.review.ReportDTO;
import com.example.lit.domain.vo.review.ReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    // 신고하기
    public void insert(ReportVO reportVO);
    // 신고 취소(작성자)
    public void delete(Long reportNumber);

    //리포트 검색(관리자)
    public List<ReportDTO> searchReport(SearchDTO searchDTO);

    //오늘 등록된 리포트 토탈
    public int getTotalToday();

    //  차트 정보
    public Long getReportChart(String date);
}
