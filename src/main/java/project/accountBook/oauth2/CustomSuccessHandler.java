package project.accountBook.oauth2;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.accountBook.dto.CustomOAuth2User;
import project.accountBook.jwt.JwtUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String userKey = customUserDetails.getUserKey();
        String role = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("ROLE_USER");


        String token = jwtUtil.createJwt(userKey, role, 60 * 60 * 60L);

        response.addHeader("Set-Cookie", createdCookie("Authorization", token).toString());
        response.sendRedirect("http://localhost:8080/");

    }

    private ResponseCookie createdCookie(String key, String value) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60*60*60L)
                .sameSite("Strict")
                .build();

        return cookie;
    }
}
