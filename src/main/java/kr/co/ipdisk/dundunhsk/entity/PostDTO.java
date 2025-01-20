package kr.co.ipdisk.dundunhsk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    
    private Long id; // 게시글 ID
    private Long boardId; // 게시판 ID
    private String postSubject; // 게시글 제목
    private String postContent; // 게시글 내용
    private LocalDateTime postCreated; // 생성 시간
    private LocalDateTime postUpdated; // 수정 시간
    private String createUser; // 작성자 ID
    private Integer viewCount; // 조회수
    private String postLink1; // 링크 1
    private String postLink2; // 링크 2
    private String postLink3; // 링크 3
    private Boolean postIsPinned; // 상단 고정 여부
}
