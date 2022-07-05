package com.example.lit.domain.vo.messsage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MessageDTO {
    private Long sendUserNumber;
    private Long receiveUserNumber;
    private Long total;
    private String content;
    private String roomId;
    private boolean newRoomCheck;

    private Long pageNum;
    private Long amount;

    public MessageDTO() {
        this(1L,20L);
    }

    public MessageDTO(Long pageNum, Long amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }


}
