package kr.co.ipdisk.dundunhsk.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "아이디는 필수항목입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String userPwd;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String userName;

    @NotEmpty(message = "이메일은 필수항목입니다")
    private String userEmail;
}
