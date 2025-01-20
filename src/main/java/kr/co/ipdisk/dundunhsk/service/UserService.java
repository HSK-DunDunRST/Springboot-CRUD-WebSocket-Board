package kr.co.ipdisk.dundunhsk.service;

import kr.co.ipdisk.dundunhsk.entity.SiteUser;
import kr.co.ipdisk.dundunhsk.exception.DataNotFoundException;
import kr.co.ipdisk.dundunhsk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원가입 서비스 */
    public SiteUser createUser(String userId, String userPwd, String userName, String userEmail){
        SiteUser siteUser = new SiteUser();
        siteUser.setUserId(userId);
        siteUser.setUserPwd(passwordEncoder.encode(userPwd));
        siteUser.setUserName(userName);
        siteUser.setUserEmail(userEmail);
        siteUser.setSignUpDate(LocalDateTime.now());
        userRepository.save(siteUser);

        return siteUser;
    }

    public SiteUser getUserName(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUserName(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없음.");
        }
    }

    public SiteUser getCurrentLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        return this.userRepository.findByUserName(currentUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUser));
    }

}
