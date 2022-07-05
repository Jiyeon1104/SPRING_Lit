package com.example.lit.domain.vo;

import com.example.lit.domain.vo.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class SearchPageDTO<T> {
    private List<T> list;
    private Long total;
    private SearchDTO searchDTO;

    private int startPage;  //한 페이지 블록의 시작 페이지
    private int endPage;    //블록의 끝 페이지
    private int realEnd;    //전체 페이지 중 마지막 페이지 숫자
    private int pageCount;  //한 블록당 몇개의 페이지를 표시하는지 저장하는 필드
    private boolean prev, next; // 이전, 다음 여부를 boolean으로 저장

    //pageCount를 자동으로 10으로 초기화 하는 생성자 (연산은 다음 생성자에서 처리)
    public SearchPageDTO(List<T> list, SearchDTO searchDTO, Long total) {
        this(list, searchDTO, 10, total);
    }

    public SearchPageDTO(List<T> list, SearchDTO searchDTO, int pageCount, Long total) {
        this.list = list;
        this.searchDTO = searchDTO;
        this.total = total;

        //블록의 끝 페이지 번호 = (현재 페이지번호 / 한 블럭당 페이지 수)를 올림 처리  *  한 블럭당 페이지 수
        this.endPage = (int)Math.ceil(searchDTO.getPageNum() / (double)pageCount) * pageCount;
        //블록의 시작 페이지 번호는 = 끝 페이지 번호 - 한 블럭당 페이지 수 + 1
        this.startPage = this.endPage - pageCount + 1;
        //전체 페이지 중 끝 페이지 번호 = (전체 게시글 수 / 한 페이지 당 게시글 수) 를 올림 처리
        this.realEnd = (int)Math.ceil((double)total / searchDTO.getAmount());

        if(realEnd < this.endPage){ //블록의 마지막보다 실제 마지막 페이지가 작다면
            this.endPage = realEnd; //블록의 마지막 = 실제 마지막 페이지
        }

        this.prev = this.startPage > 1; //이전 여부 = 블럭의 시작 페이지 > 1
        this.next = this.endPage < realEnd; //다음 여부 = 블럭의 끝 페이지 < 실제 마지막 페이지
    }

}
