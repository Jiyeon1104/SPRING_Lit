package com.example.lit.mapper.project;

import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.review.ReviewFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectFileMapper {
    // 프로젝트 이미지 넣기
    public void insert(ProjectFileVO projectFileVO);
    // 프로젝트 이미지 삭제
    public void delete(Long projectNumber);
    // 사진 리스트
    public ProjectFileVO getImg(Long projectNumber);
    // DB에 없는 이미지 삭제
    public List<ProjectFileVO> getOldFiles();
    // 내 프로젝트 이미지 가져오기
    public List<ProjectFileVO> getMyProject(Long userNumber);
}
