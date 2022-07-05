package com.example.lit.domain.vo.messsage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MessageVO {
    private Long messageNumber;
    private Long sendUserNumber;
    private Long receiveUserNumber;
    private String roomId;
    private String content;
    private String messageDate;

    private MessageType type;
}
