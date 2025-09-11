package project.accountBook.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.JoinRequest;
import project.accountBook.dto.LoginRequest;
import project.accountBook.entity.User;
import project.accountBook.login.JwtUtil;
import project.accountBook.repository.UserRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public Long join(JoinRequest joinRequest) {
        String encodedPassword = encoder.encode(joinRequest.getPw());

        User user = new User(joinRequest.getUsername(), encodedPassword, "ROLE_USER");

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }


    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if(user != null) {
            if(!encoder.matches(loginRequest.getPw(), user.getPassword())) {
                throw new RuntimeException("로그인 실패: 비밀번호 불일치");
            }
        }
        String accessToken = jwtUtil.createJwt(user.getId().toString(), user.getRole(), "access", 60L * 5 * 1000);
        String refreshToken = jwtUtil.createJwt(user.getId().toString(), user.getRole(), "refresh", 365L * 24 * 3600000);

        response.addHeader("Set-Cookie", jwtUtil.createdCookie("access", accessToken, 60L * 5).toString());
        response.addHeader("Set-Cookie", jwtUtil.createdCookie("refresh", refreshToken, 365L * 24 * 3600).toString());
    }
    public User userNameDuplicationCheck(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
