package com.example.lit.service.project;

import com.example.lit.domain.dao.project.ParticipationDAO;
import com.example.lit.domain.dao.project.ProjectDAO;
import com.example.lit.domain.dao.project.ProjectFileDAO;
import com.example.lit.domain.dao.user.achievement.AchievementDAO;
import com.example.lit.domain.vo.Criteria;
import com.example.lit.domain.vo.ListDTO;
import com.example.lit.domain.vo.SearchDTO;
import com.example.lit.domain.vo.project.ParticipationVO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.project.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LitServiceImple implements LitService{
    private final ProjectDAO projectDAO;
    private final ProjectFileDAO projectFileDAO;
    private final AchievementDAO achievementDAO;
    private final ParticipationDAO participationDAO;


    @Override
    public List<ProjectVO> getList(ListDTO listDTO) {
        return projectDAO.getList(listDTO);
    }

    @Override
    //트랜잭션으로 묶어서 처리
    @Transactional(rollbackFor = Exception.class)
    public void register(ProjectVO projectVO) {

        //프로젝트 생성
        projectDAO.register(projectVO);

        ParticipationVO participationVO = new ParticipationVO();
        participationVO.setProjectNumber(projectVO.getProjectNumber());
        participationVO.setUserNumber(projectVO.getUserNumber());
        participationVO.setStatus(0L);
        participationDAO.register(participationVO);

        //프로젝트 이미지 처리(1장)
        if(projectVO.getProjectFile() != null){
            ProjectFileVO projectFileVO = projectVO.getProjectFile();
            projectFileVO.setProjectNumber(projectVO.getProjectNumber());

            projectFileDAO.register(projectFileVO);
        }

        // 2번째 메달 - 첫 lit 생성하기
        if(getTotalByUserNumber(projectVO.getUserNumber()) == 1){
            achievementDAO.insertMedal(projectVO.getUserNumber(), "2");
        }
    }

    @Override
    public ProjectDTO read(ProjectDTO projectDTO) {
        Long projectNumber = projectDTO.getProjectNumber();
        Long userNumber = projectDTO.getUserNumber();

        ParticipationVO participationVO = new ParticipationVO();
            participationVO.setProjectNumber(projectNumber);
            participationVO.setUserNumber(userNumber);

       projectDTO = projectDAO.read(projectNumber);                                     // 기본 데이터 겟
            projectDTO.setReviewCount( projectDAO.reviewTotal(projectNumber) );         // 게시물 토탈
            projectDTO.setParticipationCount( projectDAO.challengeTotal(projectNumber));// 참가자 토탈
            projectDTO.setParticipationStatus( participationDAO.select( participationVO ).getStatus() );
            projectDTO.setUserNumber( userNumber );

        return projectDTO;
    }

    @Override
    public boolean remove(Long projectNumber) {
        return projectDAO.remove(projectNumber);
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(ParticipationVO participationVO) {
        participationDAO.register(participationVO);
        Long projectNumber = participationVO.getProjectNumber();
        projectDAO.increaseApplyCnt(projectNumber);
    }

    @Override
    public void modify(ParticipationVO participationVO, Long result) {

    }

    @Override
    public void removeImg(Long projectNumber) {

    }

    @Override
    public List<ProjectFileVO> getOldFiles() {
        return null;
    }

    @Override
    public ProjectFileVO getImg(Long projectNumber) {
        return projectFileDAO.getImg(projectNumber);
    }

    @Override
    public List<ProjectDTO> searchProject(SearchDTO searchDTO) {
        return projectDAO.searchProject(searchDTO);
    }

    @Override
    public void changeStatus(Long projectNumber, Long status) {
        projectDAO.changeStatus(projectNumber, status);
    }

    @Override
    public int getTotalByStatus(Long status) {
        return projectDAO.getTotalByStatus(status);
    }

    @Override
    public List<ProjectDTO> getMainList(ListDTO listDTO) {
        List<ProjectDTO> result = projectDAO.getMainList(listDTO).stream().map( project -> {
            ProjectFileVO projectFileVO = projectFileDAO.getImg(project.getProjectNumber());
            project.setProjectFile(projectFileVO);
            return project;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public int getTotalByUserNumber(Long userNumber) { return projectDAO.getTotalByUserNumber(userNumber); }

    @Override
    public List<ProjectVO> getMyList(ListDTO listDTO) {
        List<ProjectVO> result = new ArrayList<>();
        for(ProjectVO project : projectDAO.getMyList(listDTO)){
            project.setProjectFile(projectFileDAO.getImg(project.getProjectNumber()));
            result.add(project);
        }
        return result;
    }
}
