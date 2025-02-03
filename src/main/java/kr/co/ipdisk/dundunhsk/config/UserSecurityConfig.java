package kr.co.ipdisk.dundunhsk.config;

import kr.co.ipdisk.dundunhsk.entity.SiteUser;
import kr.co.ipdisk.dundunhsk.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserSecurityConfig implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByUserId(userLoginId);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException(userLoginId + " 아이디가 존재하지 않습니다.");
        }
        SiteUser user = _siteUser.get();

        user.setRecentLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }
}