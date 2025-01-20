package kr.co.ipdisk.dundunhsk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ipdisk.dundunhsk.entity.ChatRoomEntity;
import kr.co.ipdisk.dundunhsk.repository.ChatRoomRepository;
import kr.co.ipdisk.dundunhsk.repository.DynamicRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatRoomService {

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    private final DynamicRepository dynamicRepository;

    // 대화방 전체 조회
    public List<ChatRoomEntity> findAllChatRoom(){
        return chatRoomRepository.findAll();
    }

    // 대화방 생성
    public ChatRoomEntity createChatRoom(ChatRoomEntity chatRoomEntity){
        try {
            dynamicRepository.createDynamicChatRoom(chatRoomEntity.getChatDataTable());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException("채팅방 생성에 실패하였습니다.");
        }
        return chatRoomRepository.save(chatRoomEntity);
    }

    // 대화방 삭제 (parameter : roomId)
    public void deleteChatRoom(Long roomId){
        chatRoomRepository.deleteById(roomId);
    }

    // 특정 대화방 조회 (parameter : roomId)
    public ChatRoomEntity findChatRoomByid(Long roomId){
        return chatRoomRepository.findById(roomId).orElse(null);
    }
    
}
