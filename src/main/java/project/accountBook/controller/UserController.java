package project.accountBook.controller;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.accountBook.dto.JoinRequest;
import project.accountBook.dto.LoginRequest;
import project.accountBook.service.UserService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity<String> join(@RequestBody @Valid JoinRequest joinRequest, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors().toString());
        }
        else if (userService.userNameDuplicationCheck(joinRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("아이디가 이미 존재합니다.");
        }
        else if (!joinRequest.getPw().equals(joinRequest.getCfpw())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        Long id = userService.join(joinRequest);

        return ResponseEntity.ok().body("id: " + id + "\n" + "username: " + joinRequest.getUsername() + "\n"
                + "pw: " + joinRequest.getPw());
    }

    @GetMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response, BindingResult result){
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors().toString());
        }
        try {
            userService.login(loginRequest, response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("유저를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok().body("로그인 성공");
    }



}
