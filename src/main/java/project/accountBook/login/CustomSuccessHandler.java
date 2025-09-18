package project.accountBook.login;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.accountBook.dto.CustomUserPrincipal;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserPrincipal customUserDetails = (CustomUserPrincipal) authentication.getPrincipal();

        String userId = customUserDetails.getUserId();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");


        String accessToken = jwtUtil.createJwt(userId.toString(),"access", role, 60L * 5 * 1000);
        String refreshToken = jwtUtil.createJwt(userId.toString(),"refresh", role, 365L * 24 * 3600000);
        response.addHeader("Set-Cookie", jwtUtil.createdCookie("access", accessToken, 60L * 5).toString());
        response.addHeader("Set-Cookie", jwtUtil.createdCookie("refresh", refreshToken, 365L * 24 * 3600).toString());
        response.sendRedirect("http://localhost:5173/");
    }

}
