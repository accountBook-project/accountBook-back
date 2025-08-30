package project.accountBook.login;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LogoutFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("/api/logout".equals(request.getRequestURI())) {
            ResponseCookie access = ResponseCookie.from("access")
                    .maxAge(0)
                    .path("/")
                    .build();
            ResponseCookie refresh = ResponseCookie.from("refresh")
                    .maxAge(0)
                    .path("/")
                    .build();
            response.addHeader("Set-Cookie", refresh.toString());
            response.addHeader("Set-Cookie", access.toString());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
