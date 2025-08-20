package project.accountBook.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class JoinRequest {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{5,12}$", message = "아이디는 필수입니다.")
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,16}$", message = "비밀번호 형식이 올바르지 않습니다.")
    private String pw;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,16}$", message = "비밀번호 확인 형식이 올바르지 않습니다.")
    private String cfpw;
}
