package com.example.lit.domain.dao.project;

import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectVO;
import com.example.lit.mapper.project.ParticipationMapper;
import com.example.lit.mapper.project.ProjectMapper;
import com.example.lit.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectDAO {
    private final ProjectMapper projectMapper;
    private final ReviewMapper reviewMapper;
    private final ParticipationMapper participationMapper;
    //    프로젝트 목록 가져오기
    public List<ProjectVO> getList(ListDTO listDTO){ return projectMapper.getList(listDTO); }
    //    프로젝트 등록
    public void register(ProjectVO projectVO){ projectMapper.insert(projectVO);}
    //    프로젝트 상세보기
    public ProjectDTO read(Long projectNumber){ return projectMapper.get(projectNumber); }
    //    프로젝트 상세보기(리뷰작성용)
    public ProjectDTO getForReview(Long projectNumber, Long userNumber){
        return projectMapper.getForReview(projectNumber, userNumber);
    };
    //    프로젝트 삭제
    public boolean remove(Long projectNumber){ return projectMapper.delete(projectNumber) != 0; }
    //    프로젝트 전체 개수
    public int getTotal(){ return projectMapper.getTotal(); }
    //    프로젝트 검색
    public List<ProjectDTO> searchProject(SearchDTO searchDTO){ return projectMapper.searchProject(searchDTO); }
    //  프로젝트 승인(상태변경)
    public void changeStatus(Long projectNumber, Long status){
        projectMapper.changeStatus(projectNumber, status);
    }
    // 프로젝트 상태로 토탈 가져오기
    public int getTotalByStatus(Long status){ return projectMapper.getTotalByStatus(status); }

    //메인 리스트
    public List<ProjectDTO> getMainList(ListDTO listDTO) { return projectMapper.getMainList(listDTO); }

    // 2번째 메달 - 첫 lit 생성하기
    public int getTotalByUserNumber(Long userNumber){return projectMapper.getTotalByUserNumber(userNumber); }

    // 인증 전체 개수
    public Long reviewTotal(Long projectNumber){ return reviewMapper.reviewTotal( projectNumber ); }
    // 도전 전체 개수
    public Long challengeTotal(Long projectNumber){ return participationMapper.challengeTotal( projectNumber ); }

    // 마이페이지 프로젝트 리스트
    public List<ProjectVO> getMyList(ListDTO listDTO){ return projectMapper.getMyList(listDTO); }

    // apply count 증가 시키기
    public void increaseApplyCnt(Long projectNumber){ projectMapper.increaseApplyCnt(projectNumber); }


}
