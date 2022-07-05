package com.example.lit.mapper.user;

import com.example.lit.domain.vo.user.AlertDTO;
import com.example.lit.domain.vo.user.AlertVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {

    // 좋아요 유저한번호와 자신의번호를 저장
    public void insert(AlertVO alertVO);

    // 팔로우 할시 알림
    public void alertFollow(AlertVO alertVO);

    // 로그인한 유저 번호로 알림리스트 가져오기
    public List<AlertDTO> alertList(Long userNumber);

    public int getAlertNumber(AlertVO alertVO);

    // 좋아요 취소시 또는 팔로우 취소시 삭제
    public void remove(int alertNumber);

}
