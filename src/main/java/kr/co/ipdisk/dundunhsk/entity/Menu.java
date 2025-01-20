package kr.co.ipdisk.dundunhsk.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "nav_config")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String menuName;  // 메뉴 이름

    private String boardLink = null; // 게시판 선택 시 게시판 테이블명

    private String extraLink = null;   // 외부 링크 URL (외부 링크 선택 시 사용)

    private Integer menuOrder; // 메뉴 순서

    private Boolean openInNewTab; // 새창 열기 여부

    @Column(nullable = false)
    private String menuType;  // 메뉴 유형 ("board" or "externalLink")
}
