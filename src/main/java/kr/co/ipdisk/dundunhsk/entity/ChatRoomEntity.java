package kr.co.ipdisk.dundunhsk.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "room_data")
public class ChatRoomEntity {
    
    // 채팅방 번호
    @Column(name = "room_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    // 채팅방 Table 이름
    @Column(name = "table_name")
    private String chatDataTable;

    // 채팅방 이름
    @Column(name = "room_name")
    private String chatRoomName;

    // 채팅방 추가 설명 ( 필수 아님 )
    @Column(name = "room_description")
    private String chatRoomDescription;

    // 채팅방 최대 참가 인원수
    @Column(name = "room_max_join")
    private Long chatRoomMaxJoiner;

    // 채팅방 비밀방 여부 ( 기본값 비활성화 )
    @Column(name = "room_security")
    private Boolean chatRoomSecurity = false;

    // 채팅방 비밀번호 ( 비밀방일 경우 )
    @Column(name = "room_password", columnDefinition = "VARCHAR(4)")
    private String chatRoomPassword;

    // 채팅방 생성 타임스탬프
    @Column(name = "room_create_time")
    private LocalDateTime chatRoomCreateTime;

    // Table이 생성될 때
    @PrePersist
    public void onCreate(){
        this.chatRoomCreateTime = LocalDateTime.now();
    }
}
