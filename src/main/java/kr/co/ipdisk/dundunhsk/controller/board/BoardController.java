package kr.co.ipdisk.dundunhsk.controller.board;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ipdisk.dundunhsk.service.BoardService;
import kr.co.ipdisk.dundunhsk.service.UserService;
import kr.co.ipdisk.dundunhsk.service.PostService;
import kr.co.ipdisk.dundunhsk.entity.PostDTO;
import kr.co.ipdisk.dundunhsk.entity.Board;

/*
    * 로그인 없이 게시판, 게시글 조회 및 요청 컨트롤러
    * @AutoWired
        ^ BoardService - 게시판 생성 & 수정 & 삭제 처리 서비스
        ^ PostService - 게시글 생성 & 수정 & 삭제 처리 서비스
        ^ UserService
        ^ JdbcTemplate
    * @LastModifyDate 최종수정날짜 2024. 11. 13
    * @version 1.0, Copyright © DunDunHSK.ipdisk.co.kr All Rights Reserved
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;
    @Autowired
    private final PostService postService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    
    // 게시글 목록 페이지 요청
    @GetMapping("/{boardTable}")
    public String listPosts(@PathVariable("boardTable") String boardTable, Model model, @ModelAttribute PostDTO postDTO) {
        // 테이블 목록 테이블에서 조회
        Optional<Board> board = boardService.findByTableName(boardTable);
        String boardName = board.get().getBoardName();
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardTable", boardTable);
        model.addAttribute("postList", jdbcTemplate.queryForList("SELECT * FROM " + "tbl_" + boardTable));
        return "views/board_view";
    }

    //게시글 작성 페이지 요청
    @GetMapping("/{boardTable}/create/")
    public String createPost(@PathVariable("boardTable")  String boardTable, Model model) {
        PostDTO postDTO = new PostDTO(); // DTO 초기화
        model.addAttribute("boardTable", boardTable);
        model.addAttribute("PostDTO", postDTO); // 모델에 추가
        
        // * Debug 전용 출력문
        // System.out.println("TEST Print ID: " + id);  // PASS

        return "form/post/post_create";
    }

    // 게시글 생성 처리
    @PostMapping("/{boardTable}/create")
    public String createPost(@PathVariable("boardTable") String boardTable, @ModelAttribute("postDTO") PostDTO postDTO, Model model) {
        Optional<Board> board = boardService.findByTableName(boardTable);
        String boardName = board.get().getBoardName();
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardTable", boardTable);

        postDTO.setCreateUser(userService.getCurrentLoginUser().getUserId()); // 현재 사용자 ID를 가져오는 로직 추가 필요  
        String insertPostQuery = String.format("INSERT INTO %s (board_id, post_subject, post_content, create_user, post_link1, post_link2, post_isPinned) VALUES (?, ?, ?, ?, ?, ?, ?)", "tbl_" + boardTable);
        jdbcTemplate.update(insertPostQuery, 
                                board.get().getId(), postDTO.getPostSubject(), postDTO.getPostContent(), postDTO.getCreateUser(), 
                                postDTO.getPostLink1(), postDTO.getPostLink2(), postDTO.getPostIsPinned()
                            );

        return "redirect:/board/{boardTable}"; // 리디렉션 경로 수정
    }

    // 게시글 보기 
    @GetMapping("/{boardTable}/{postId}")
    public String viewPost(@PathVariable String boardTable, @PathVariable Long postId, @ModelAttribute PostDTO postDTO, Model model) {
        PostDTO post = postService.getPost(boardTable, postId);
        model.addAttribute("post", post);
        model.addAttribute("boardTable", boardTable);

        // * Debug 전용 출력문
        // System.out.println("Post Subject: " + post.getPostSubject());
        // System.out.println("Link1: " + post.getPostLink1());
        // System.out.println("Link2: " + post.getPostLink2());

        return "views/post_view";
    }
}
