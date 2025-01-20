package kr.co.ipdisk.dundunhsk.entity;

import java.time.LocalDateTime;

import groovy.transform.ToString;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatDTO {

    public enum MessageType{
        CHAT, JOIN, LEAVE
    }

    // 채팅 id
    private Long chatId;
    // 채팅방 id
    private Long roomId;
    // 채팅 전송 타입
    private MessageType messageType;
    // 채팅 전송자 이름
    private String sender;
    // 채팅 전송 내용
    private String content;
    // 채팅 전송 시간
    private LocalDateTime sendDateTime;
}
