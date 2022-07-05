package com.example.lit.mapper;

import com.example.lit.domain.vo.user.FollowVO;
import com.example.lit.mapper.user.FollowMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FollowMapperTest {

    @Autowired
    private FollowMapper followMapper;

    @Test
    public void insert(){
        log.info("---------------------");
        FollowVO followVO = new FollowVO();
        followVO.setFollowerNumber(4L);
        followVO.setFollowingNumber(2L);
//        log.info( "insert : " + followMapper.insert(followVO) );
        log.info("---------------------");
    }
    @Test
    public void delete(){
        log.info("---------------------");
        FollowVO followVO = new FollowVO();
        followVO.setFollowerNumber(1L);
        followVO.setFollowingNumber(4L);
//        log.info( "delete : " + followMapper.delete(followVO) );
        log.info("---------------------");
    }


    @Test
    public void followingCount(){
        log.info("---------------------");
        FollowVO followVO = new FollowVO();
        followVO.setFollowingNumber(4L);
        log.info( "followingCount : " + followMapper.followingCount(followVO) );
        log.info("---------------------");
    }
    @Test
    public void followerCount(){
        log.info("---------------------");
        FollowVO followVO = new FollowVO();
        followVO.setFollowerNumber(1L);
        log.info( "followerCount : " + followMapper.followerCount(followVO) );
        log.info("---------------------");
    }

    @Test
    public void folloCheck(){
        FollowVO followVO = new FollowVO();
        followVO.setFollowingNumber(1L);
        followVO.setFollowerNumber(3L);
        log.info(followMapper.followingCheck(followVO) + "");
    }

//    @Test
//    public void insert(){
//        log.info("---------------------");
//        FollowVO followVO = new FollowVO();
//        followVO.setFollowerNumber(1L);
//        followVO.setFollowingNumber(2L);
//        log.info( "insert : " + followMapper.insert(followVO) );
//        log.info("---------------------");
//    }
////    @Test
//    public void delete(){
//        log.info("---------------------");
//        FollowVO followVO = new FollowVO();
//        log.info( "delete : " + followMapper.delete(followVO) );
//        log.info("---------------------");
//    }
//    @Test
//    public void followingCount(){
//        log.info("---------------------");
//        FollowVO followVO = new FollowVO();
//        log.info( "followingCount : " + followMapper.followingCount(followVO) );
//        log.info("---------------------");
//    }
//    @Test
//    public void followerCount(){
//        log.info("---------------------");
//        FollowVO followVO = new FollowVO();
//        log.info( "followerCount : " + followMapper.followerCount(followVO) );
//        log.info("---------------------");
//    }

}
