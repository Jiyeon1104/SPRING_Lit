package com.example.lit.mapper.user;

import com.example.lit.domain.vo.project.ProjectFileVO;
import com.example.lit.domain.vo.review.ReviewFileVO;
import com.example.lit.domain.vo.user.UserFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFileMapper {
    //유저 파일 업로드
    public void insert(UserFileVO userFileVO);
    //유저 파일 지우기
    public void delete(Long userNumber);
    //유저 파일 수정
    public void update(UserFileVO userFileVO);
    // 사진 리스트
    public UserFileVO getImg(Long userNumber);
    // DB에 없는 이미지 삭제
    public List<UserFileVO> getOldFiles();


}
