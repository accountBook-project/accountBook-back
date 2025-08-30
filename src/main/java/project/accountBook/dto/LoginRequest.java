package project.accountBook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String pw;
}
