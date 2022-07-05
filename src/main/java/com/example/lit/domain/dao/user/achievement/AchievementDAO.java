package com.example.lit.domain.dao.user.achievement;

import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.user.achievement.AchievementVO;
import com.example.lit.mapper.user.achievement.AchievementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AchievementDAO {
    private final AchievementMapper achievementMapper;

    //메달 추가
    public void register(AchievementVO achievementVO){ achievementMapper.insert(achievementVO); }
    //메달 전체 해금 유무 (?)
    public List<AchievementVO> getList(Long userNumber){ return achievementMapper.getList(userNumber); }
    //메달 하나 보기
    public AchievementVO read(Long userNumber){ return achievementMapper.get(userNumber); }
    //메달 획득
    public void insertMedal(Long userNumber, String medal){
        achievementMapper.insertMedal(userNumber, medal);
    }
    //4번째 메달 조건 - 메달 10개 모으기
    public int medal4Condition(Long userNumber) { return achievementMapper.medal4Condition(userNumber); }
    // 5번째 메달 조건 - 전체 카테고리 litUp 100회 성공
    public int medal5Condition(Long userNumber) {return achievementMapper.medal5Condition(userNumber); }
    // 6번째 메달 조건 - lits 10번 생성하기 성공
    public int medal6Condition(Long userNumber) {return achievementMapper.medal6Condition(userNumber); }
    // 7번째 메달 조건 - 생활 카테고리 5회 성공하기
    public int medal7Condition(Long userNumber, String category) {return achievementMapper.medal5CntCondition(userNumber, category); }
    // 8번째 메달 조건 - 운동 카테고리 5회 성공하기
    public int medal8Condition(Long userNumber, String category) {return achievementMapper.medal5CntCondition(userNumber, category); }
    // 9번째 메달 조건 - 정서 카테고리 5회 성공하기
    public int medal9Condition(Long userNumber, String category) {return achievementMapper.medal5CntCondition(userNumber, category); }
    // 10번째 메달 조건 - 취미 카테고리 5회 성공하기
    public int medal10Condition(Long userNumber, String category) {return achievementMapper.medal5CntCondition(userNumber, category); }
    // 11번째 메달 조건 - 예술 카테고리 5회 성공하기
    public int medal11Condition(Long userNumber, String category) {return achievementMapper.medal5CntCondition(userNumber, category); }
    // 메달 1번이상 인서트 되는 것 막아주기
    public int medalInsertBlock(Long userNumber, String medal) {return achievementMapper.medalInsertBlock(userNumber, medal); }
    // 12번째 메달 조건 - 댓글 1000번 달성하기
    public int medal12Condition(Long userNumber){ return  achievementMapper.medal12Condition(userNumber); }
    // 13번째 메달 조건 - 좋아요 1000번 달성하기
    public int medal13Condition(Long userNumber){return  achievementMapper.medal13Condition(userNumber); }
    // 14번째 메달 조건 - 팔로잉 100명이상 만들기
    public int medal14Condition(Long userNumber){ return achievementMapper.medal14Condition(userNumber); }
    // 15번째 메달 조건 - 팔로워 100만명이상 만들기
    public int medal15Condition(Long userNumber){return achievementMapper.medal15Condition(userNumber);}

}
