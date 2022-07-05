package com.example.lit.domain.vo.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyPageDTO {
    private List<ReplyVO> list;
    private int total;
}
