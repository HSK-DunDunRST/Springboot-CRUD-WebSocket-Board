package kr.co.ipdisk.dundunhsk.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ipdisk.dundunhsk.form.LoginForm;
import kr.co.ipdisk.dundunhsk.form.SignupForm;
import kr.co.ipdisk.dundunhsk.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService; // UserService를 주입받아 사용

    /* 로그인 페이지 요청 */
    @GetMapping("/login")
    public String login(LoginForm loginForm) {
        return "form/login/login"; // 로그인 페이지로 이동
    }

    /* 회원가입 페이지 요청 */
    @GetMapping("/signup")
    public String signup(SignupForm signupForm) {
        return "form/login/signup"; // 회원가입 페이지로 이동
    }

    /* 회원가입 처리 요청 */
    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // 폼 데이터에 오류가 있는지 확인
            return "form/login/signup"; // 오류가 있을 경우 다시 회원가입 페이지로 이동
        }
        try {
            // 사용자 생성 로직 호출
            userService.createUser(signupForm.getUserId(), signupForm.getUserPwd(), signupForm.getUserName(), signupForm.getUserEmail());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            // 데이터 무결성 위반 예외 처리 (예: 중복된 사용자 ID)
            dataIntegrityViolationException.printStackTrace();
            bindingResult.reject("signupFailed", "이미 가입된 사용자입니다."); // 오류 메시지 설정
            return "form/login/signup"; // 오류가 있을 경우 다시 회원가입 페이지로 이동
        } catch (Exception exception) {
            // 기타 예외 처리
            exception.printStackTrace();
            bindingResult.reject("signupFailed", exception.getMessage()); // 오류 메시지 설정
        }
        return "redirect:/"; // 회원가입 성공 시 메인 페이지로 리다이렉트
    }
}
