package com.example.lit.mapper.user.achievement;

import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.domain.vo.user.achievement.AchievementVO;
import com.example.lit.domain.vo.user.achievement.MedalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AchievementMapper {
    //메달 추가
    public void insert(AchievementVO achievementVO);

    //메달 전체 해금 유무 (?) 이거 조건은 어떻게 같이 표시함?
    public List<AchievementVO> getList(Long userNumber);

    //메달 하나 보기 이거 조건은 어떻게 같이 표시함?
    public AchievementVO get(Long userNumber);

    // 메달 획득
    public void insertMedal(@Param("userNumber") Long userNumber, @Param("medal") String medal);

    // 메달 1번이상 인서트 되는 것 막아주기
    public int medalInsertBlock(Long userNumber, String medal);

    // 4번째 메달 조건 - 메달 10개 모으기
    public int medal4Condition(Long userNumber);

    // 5번째 메달 조건 - 전체 카테고리 litUp 100회 성공
    public int medal5Condition(Long userNumber);

    // 6번째 메달 조건 - lits 10번 생성하기 성공
    public int medal6Condition(Long userNumber);

    // 7~11번째 카테고리 5회 성공하기
    public int medal5CntCondition(Long userNumber, String category);

    // 12번째 메달 조건 - 댓글 1000번 달성하기
    public int medal12Condition(Long userNumber);

    // 13번째 메달 조건 - 좋아요 1000번 달성하기
    public int medal13Condition(Long userNumber);

    // 14번째 메달 조건 - 팔로잉 100명이상 만들기
    public int medal14Condition(Long userNumber);

    // 15번째 메달 조건 - 팔로워 100만명이상 만들기
    public int medal15Condition(Long userNumber);


}
