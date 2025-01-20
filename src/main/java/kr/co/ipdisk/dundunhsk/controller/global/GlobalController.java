package kr.co.ipdisk.dundunhsk.controller.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.ipdisk.dundunhsk.entity.Menu;
// import kr.co.ipdisk.dundunhsk.repository.UserRepository;
import kr.co.ipdisk.dundunhsk.service.MenuService;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private MenuService menuService;
    
    // @Autowired
    // private UserRepository userRepository; 

    @ModelAttribute("isAdmin") // 뷰에서 사용할 속성 이름
    public boolean isAdmin(Authentication authentication) {
        if (authentication != null) { // 인증 정보가 있을 경우
            return authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")); // ADMIN 권한 확인
        }
        return false; // 인증되지 않은 경우 ADMIN 권한 없음
    }

    @ModelAttribute
    public void addMenuList(Model model) {
        List<Menu> menuList = menuService.findAll(); // 메뉴 리스트 가져오기
        model.addAttribute("menuList", menuList); // 모델에 추가\
        System.out.println("MenuList >> " + menuList.toString());
    }
}
