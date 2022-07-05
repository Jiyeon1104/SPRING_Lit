package com.example.lit.domain.dao.project;

import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.mapper.project.ProjectFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectFileDAO {
    private final ProjectFileMapper projectFileMapper;
    // 프로젝트 이미지 넣기
    public void register(ProjectFileVO projectFileVO){ projectFileMapper.insert(projectFileVO); }
    // 프로젝트 이미지 삭제
    public void remove(Long projectNumber){ projectFileMapper.delete(projectNumber); }
    // 사진 리스트
    public ProjectFileVO getImg(Long projectNumber){ return projectFileMapper.getImg(projectNumber); }
    // DB에 없는 이미지 삭제
    public List<ProjectFileVO> getOldFiles(){ return projectFileMapper.getOldFiles(); }
    // 나의 프로젝트 가져오기(리뷰 선택용)
    public List<ProjectFileVO> getMyProject(Long userNumber){ return projectFileMapper.getMyProject(userNumber);}
}
