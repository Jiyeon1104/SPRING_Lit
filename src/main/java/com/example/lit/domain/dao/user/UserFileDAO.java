package com.example.lit.domain.dao.user;

import com.example.lit.domain.vo.user.UserFileVO;
import com.example.lit.mapper.user.UserFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserFileDAO {
    private final UserFileMapper userFileMapper;

    //유저 파일 업로드
    public void register(UserFileVO userFileVO){ userFileMapper.insert(userFileVO); }
    //유저 파일 지우기
    public void remove(Long userNumber){ userFileMapper.delete(userNumber); }
    //유저 파일 수정
    public void modify(UserFileVO userFileVO){ userFileMapper.update(userFileVO); }
    // 사진
    public UserFileVO getImg(Long userNumber){ return userFileMapper.getImg(userNumber); }
    // DB에 없는 이미지 삭제
    public List<UserFileVO> getOldFiles(){ return userFileMapper.getOldFiles(); }
}
