package com.example.lit.mapper.user;

import com.example.lit.domain.vo.user.FollowVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
    // 팔로잉
    public int insert(FollowVO followVO);
    // 팔로잉 취소
    public int delete(FollowVO followVO);
    // 팔로잉 카운트 (내가 한 사람)
    public int followingCount(FollowVO followVO);
    // 팔로워 카운트 (다른 사람)
    public int followerCount(FollowVO followVO);

    // 팔로잉 체크
    public int followingCheck(FollowVO followVO);

}
