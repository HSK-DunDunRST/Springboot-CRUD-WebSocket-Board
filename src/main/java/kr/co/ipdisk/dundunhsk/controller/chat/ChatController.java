package kr.co.ipdisk.dundunhsk.controller.chat;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ipdisk.dundunhsk.entity.ChatDTO;
import kr.co.ipdisk.dundunhsk.service.ChatService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatController {

    @Autowired
    private final ChatService chatService;

    // 채팅 HTML 페이지 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat")
    public String chatPage(Model model, Principal principal) {
        model.addAttribute("loggedInName", principal.getName());
        return "form/chat/chat"; // 동일한 chat.html을 반환
    }

    // 특정 방으로 유저 연결
    @MessageMapping("/chat/{roomId}/.join")
    @SendTo("/room/{roomId}")
    public ChatDTO connectUser(@DestinationVariable Long roomId, ChatDTO message, SimpMessageHeaderAccessor headerAccessor, Model model) {
        model.addAttribute("roomId", roomId);
        message.setRoomId(roomId);
        message.setMessageType(ChatDTO.MessageType.JOIN);
        message.setContent(message.getSender() + "님이 방에 입장하셨습니다.");
        return message;
    }

    // 특정 방으로 메시지 전송
    @MessageMapping("/chat/{roomId}")
    @SendTo("/room/{roomId}")
    public ChatDTO sendMessage(@DestinationVariable Long roomId, ChatDTO message) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            // 메시지가 비어있다면 처리하지 않습니다.
            return null;
        }
        message.setRoomId(roomId); // 메시지에 방 ID 설정
        chatService.saveMessage(message.getRoomId(), message);
        System.out.println("Saved Message Data for Room " + roomId + ": " + message.toString());
        return message; // 메시지를 다시 해당 방 사용자들에게 보내줍니다.
    }
}