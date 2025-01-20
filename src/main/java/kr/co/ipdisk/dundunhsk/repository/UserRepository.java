package kr.co.ipdisk.dundunhsk.repository;

import kr.co.ipdisk.dundunhsk.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUserId(String userLoginId);
    Optional<SiteUser> findByUserName(String userName);
}
