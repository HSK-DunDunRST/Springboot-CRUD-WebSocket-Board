package kr.co.ipdisk.dundunhsk.config;

import kr.co.ipdisk.dundunhsk.entity.SiteUser;
import kr.co.ipdisk.dundunhsk.entity.UserRole;
import kr.co.ipdisk.dundunhsk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityConfig implements UserDetailsService {

    private final UserRepository userRepository; // UserRepository 주입받아 사용

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
//        System.out.println("UserLoginID: " + userLoginId);
        Optional<SiteUser> _siteUser = this.userRepository.findByUserId(userLoginId); // 사용자 ID로 사용자 검색
//        System.out.println(_siteUser.get().getUserId());
        if(_siteUser.isEmpty()){
//            System.out.println(userLoginId + " 아이디가 존재하지 않습니다.");
            throw new UsernameNotFoundException(userLoginId + " 아이디가 존재하지 않습니다."); // 사용자를 찾지 못할 경우 예외 발생
        }
        SiteUser user = _siteUser.get(); // 사용자가 존재할 경우 SiteUser 객체 반환
//        System.out.println(user.getUserId()+ " & " + user.getUserPwd());

        user.setRecentLogin(LocalDateTime.now());
        userRepository.save(user);

        List<GrantedAuthority> authorityList = new ArrayList<>(); // 권한 목록 생성
        if("admin".equals(userLoginId)){
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue())); // 관리자 권한 부여
        } else{
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue())); // 일반 사용자 권한 부여
        }
        // User 객체를 생성하여 반환 (Spring Security UserDetails 구현)
        // User 객체를 반환할 때 username을 사용자의 이름으로 설정
        return new User(user.getUserId(), user.getUserPwd(), authorityList) {
            @Override
            public String getUsername() {
                return user.getUserName(); // 사용자 이름을 반환
            }
        };
    }
}
