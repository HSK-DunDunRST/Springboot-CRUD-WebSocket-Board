package kr.co.ipdisk.dundunhsk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/* JPA */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_config")
public class SiteUser {
    /* 기본키 + 자동 증감(user_seq) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 유저 아이디 */
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    /* 유저 비밀번호 */
    @Column(name = "USER_PWD", nullable = false)
    private String userPwd;

    /* 유저 이름 */
    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    /* 유저 이메일 */
    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String userEmail;

    /* 유저 가입 날짜 */
    @Column(name = "SIGNUP_DATE", nullable = false)
    private LocalDateTime signUpDate;

    /* 유저 최근 로그인 날짜 */
    @Column(name ="RECENT_LOGIN")
    private LocalDateTime recentLogin;

    /* 사용자 권한 */
//    @Column(name = "USER_ROLE", columnDefinition = "VARCHAR")
//    private
    /* 사용자 이미지 (시간이 된다면 도전 ) */
}
