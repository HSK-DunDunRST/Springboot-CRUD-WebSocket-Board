package kr.co.ipdisk.dundunhsk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ipdisk.dundunhsk.entity.Menu;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByOrderByMenuOrderAsc(); // menuOrder에 따라 오름차순 정렬된 메뉴 반환
}
