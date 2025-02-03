package kr.co.ipdisk.dundunhsk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_config")
public class SiteUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_PWD", nullable = false)
    private String userPwd;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "SIGNUP_DATE", nullable = false)
    private LocalDateTime signUpDate;

    @Column(name = "RECENT_LOGIN")
    private LocalDateTime recentLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", length = 50)
    private UserRole userRole;

    // 가능할때 프로필 사진 기능 넣기

    // UserDetails 구현 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.getValue()));
    }

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}