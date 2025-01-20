package kr.co.ipdisk.dundunhsk.controller.admin;

// import lombok.NoArgsConstructor;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ipdisk.dundunhsk.service.BoardService;
import kr.co.ipdisk.dundunhsk.service.ChatRoomService;
import kr.co.ipdisk.dundunhsk.service.MenuService;
import kr.co.ipdisk.dundunhsk.entity.Board;
import kr.co.ipdisk.dundunhsk.entity.ChatRoomEntity;
import kr.co.ipdisk.dundunhsk.entity.Menu;

/*
    * 로그인 필수이며 관리자 권한이 있는 유저 접속시 관리 조회 & 요청 컨트롤러
    * @AutoWired
        ^ BoardService - 게시판 생성 & 수정 & 삭제 처리 서비스
        ^ MenuService - 빠른메뉴(nav) 생성 & 수정 & 삭제 처리 서비스
        ^ ChatRoomService - 채팅방 생성 & 수정 & 삭제 처리 서비스
    * @GetMapping
        ^ [/adm] - 관리자 설정 대시보드
        ^ [/adm/board] - 관리자 설정

    * @LastModifyDate 최종수정날짜 2025. 01. 15
    * @version 1.0, Copyright © DunDunRST.ipdisk.co.kr All Rights Reserved
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdminController {

    @Autowired
    private final BoardService boardService;
    
    @Autowired
    private final MenuService menuService;

    @Autowired
    private final ChatRoomService chatRoomService;

    // 관리지 페이지 UI 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dashboard")
    public String adminPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        return "adm/adm_index"; // admin_dashboard.html 템플릿 반환
    }

    // 게시판 관리 페이지 ( 게시판 전체 조회 )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board")
    public String listBoards(Model model) {
        model.addAttribute("boardList", boardService.findAll()); 
        model.addAttribute("board", new Board());// 모델에 추가
        return "adm/adm_board";  // Thymeleaf 템플릿으로 반환
    }

    // 게시판 생성 처리 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/create")
    public String createBoard(@ModelAttribute Board board) {
        try {
            // 게시판 이름 불러오기
            String boardTableName = board.getTableName().replaceAll("[^a-zA-Z0-9]", "_");
            board.setTableName(boardTableName);
            // 게시판 정보 저장
            boardService.boardCreate(board);
        } catch (Exception exception) {
            // 예외 처리: 로그를 기록하거나 사용자에게 알림
            exception.printStackTrace(); // 콘솔에 오류 출력 (개발 중에는 유용할 수 있음)
            return "redirect:/adm/board?error=true"; // 에러가 발생한 경우 리다이렉트
        }
        return "redirect:/adm/board"; // 정상적으로 리다이렉트
    }

    // 게시판 수정 페이지 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/modify/{boardTable}")
    public String modifyBoard(@PathVariable String boardTable, Model model){
        Optional<Board> board = boardService.findByTableName(boardTable);
        model.addAttribute("board", board);
        return "form/board/board_modify_form";
    }

    // 게시판 수정 처리 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/modify/{boardTable}")
    public String modifyBoard(@PathVariable("boardTable") String boardTable, @RequestParam("boardName") String boardName){
        boardService.modifyBoard(boardTable, boardName);
        return "redirect:/adm/board";
    }

    // 게시판 삭제 처리 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/delete/{boardTable}")
    public String deleteBoard(@PathVariable String boardTable) {
        Optional<Board> board = boardService.findByTableName(boardTable);
        
        if (board.isPresent()) {
            try {
                boardService.boardDelete(boardTable);
            } catch (Exception exception) {
                exception.printStackTrace();
                return "redirect:/adm/board?error=true"; // 에러가 발생한 경우 리다이렉트
            }
        }
        return "redirect:/adm/board";
    }

    // 채팅방 관리 목록 페이지 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat")
    public String viewChat(Model model) {
        model.addAttribute("roomList", chatRoomService.findAllChatRoom());
        model.addAttribute("chatRoom", new ChatRoomEntity());
        return "adm/adm_chat";
    }
    
    //채팅방 생성 처리 (동적 및 검증 기능 구분)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/chat/create")
    public String createChat(@ModelAttribute ChatRoomEntity chatRoomEntity, Model model) {
        
        // * Debug 전용 출력문
        System.out.println("Get Object: " + chatRoomEntity);
        System.out.println("Get Object Name: " + chatRoomEntity.getChatRoomName());

        try {
            // ! 추후 로직 개선
            // 채팅방 생성시 이름 검증
            if (chatRoomEntity.getChatRoomName() == null || chatRoomEntity.getChatRoomName().trim().isEmpty()) {
                model.addAttribute("error", "채팅방 이름은 필수항목입니다.");
                return "index";
            }
            // 채팅방 생성시 최대 인원 검증
            if (chatRoomEntity.getChatRoomMaxJoiner() == null || chatRoomEntity.getChatRoomMaxJoiner() <= 0) {
                model.addAttribute("error", "참가 가능한 최대 인원 수는 1명 이상이어야 합니다.");
                return "index";
            }
            // 채팅방 생성시 비밀번호 검증
            if (chatRoomEntity.getChatRoomSecurity() && (chatRoomEntity.getChatRoomPassword() == null || chatRoomEntity.getChatRoomPassword().length() != 4)) {
                model.addAttribute("error", "비밀번호는 숫자 4자리로 설정해야합니다.");
            }
            // 채팅방 생성시 테이블 이름 검증
            if (chatRoomEntity.getChatDataTable() == null || chatRoomEntity.getChatDataTable().trim().isEmpty()) {
                model.addAttribute("error", "테이블명이 존재하지 않습니다.");
            }
            String tableName = chatRoomEntity.getChatDataTable().replaceAll("[^a-zA-Z0-9]", "_");
            chatRoomEntity.setChatDataTable(tableName);

            // 채팅방 목록 저장
            chatRoomService.createChatRoom(chatRoomEntity);
            return "redirect:/adm/chat";

        } catch (Exception exception){
            model.addAttribute("error", "채팅방 생성 중 예기치 않은 오류가 발생하였습니다." + exception.getMessage());
            exception.printStackTrace();
            return "index";
        }
    }
    
    // 메뉴(네비) 설정 페이지 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/menu")
    public String viewMenu(Model model) {
        model.addAttribute("menuList", menuService.findAll());
        model.addAttribute("menu", new Menu());
        model.addAttribute("boardList", boardService.findAll());
        return "adm/adm_menu";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/menu/add")
    public String addMenu(@ModelAttribute Menu menu) {
        menuService.save(menu);
        return "redirect:/adm/menu";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/menu/delete/{id}")
    public String deleteMenu(@PathVariable Long id, @ModelAttribute Menu menu){
        menuService.delete(id);
        return "redirect:/adm/menu";
    }

}
