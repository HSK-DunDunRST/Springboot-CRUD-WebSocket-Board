package kr.co.ipdisk.dundunhsk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ipdisk.dundunhsk.entity.Menu;
import kr.co.ipdisk.dundunhsk.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAllByOrderByMenuOrderAsc(); // 정렬된 메뉴 리스트 반환
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
