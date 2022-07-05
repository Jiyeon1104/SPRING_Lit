package com.example.lit.domain.dao.project;

import com.example.lit.domain.vo.project.ParticipationVO;
import com.example.lit.domain.vo.project.ProjectDTO;
import com.example.lit.mapper.project.ParticipationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParticipationDAO {
    private final ParticipationMapper participationMapper;
    // 참여하기
    public void register(ParticipationVO participationVO){ participationMapper.insert(participationVO); }
    // 성공하기, 실패하기 상태변경 review에서 프로젝트 별 review 수 카운트 result로 초기화
    public void modify(ParticipationVO participationVO, Long result){ participationMapper.update(participationVO, result); }

    // 상태 값 가져오기
    public ParticipationVO select(ParticipationVO participationVO){
        return participationMapper.select(participationVO);
    }

}
