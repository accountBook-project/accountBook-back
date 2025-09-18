package project.accountBook.login;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.accountBook.dto.CustomUserPrincipal;
import project.accountBook.dto.UserDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            filterChain.doFilter(request,response);
            return;
        }

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access")) {
                token = cookie.getValue();
            } else if (cookie.getName().equals("refresh")) {
                token = jwtUtil.createJwt(jwtUtil.getUserId(cookie.getValue()), "access", jwtUtil.getRole(cookie.getValue()), 60 * 5 * 1000L);
                response.addHeader("Set-Cookie", jwtUtil.createdCookie("access", token, 60L * 5).toString());
            }
        }
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request,response);

            return;
        }
        String userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setRole(role);

        CustomUserPrincipal customOAuth2User = new CustomUserPrincipal(userDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        filterChain.doFilter(request,response);


    }
}
