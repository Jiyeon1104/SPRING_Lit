package com.example.lit.domain.dao.user;

import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.mapper.user.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FollowDAO {
    private final FollowMapper followMapper;

    // 팔로우
    public void register(FollowVO followVO){ followMapper.insert(followVO); }
    // 팔로우 취소
    public void remove(FollowVO followVO){ followMapper.delete(followVO); }
    // 팔로잉 카운트 (내가 한 사람)
    public int followingCount(FollowVO followVO){ return followMapper.followingCount(followVO); }
    // 팔로워 카운트 (다른 사람)
    public int followerCount(FollowVO followVO){ return followMapper.followerCount(followVO); }
    // 팔로잉 체크
    public int followingCheck(FollowVO followVO){ return followMapper.followingCheck(followVO); }
}
