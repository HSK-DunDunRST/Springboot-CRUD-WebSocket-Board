package kr.co.ipdisk.dundunhsk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import kr.co.ipdisk.dundunhsk.entity.ChatDTO;
import kr.co.ipdisk.dundunhsk.entity.ChatRoomEntity;
import kr.co.ipdisk.dundunhsk.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    // 메시지 DB에 저장
    public void saveMessage(Long roomId, ChatDTO chatDTO){
        // 채팅방 번호로 채팅방 목록 DB 조회
        Optional<ChatRoomEntity> findChatRoom = chatRoomRepository.findById(roomId);
        String chatTable = findChatRoom.get().getChatDataTable();
        // * Debug 전용 출력문
        System.out.println("findByRoomId: " + chatTable);

        // 채팅 데이터 테이블에 저정하는 쿼리문
        String insertMessageQuery = String.format(
            "INSERT INTO %s (chat_id, room_id, sender_name, send_message, send_date" + "VALUES (?, ?, ? ,? ,?)", chatTable);
        try {
            jdbcTemplate.update(insertMessageQuery, chatDTO.getChatId(), chatDTO.getRoomId(), chatDTO.getSender(), chatDTO.getContent(), chatDTO.getSendDateTime());
        } catch (Exception exception) {
            System.out.println("채팅 데이터 저장에 문제가 발생했습니다." + exception.getMessage());
            exception.printStackTrace();
        }

    }
}
