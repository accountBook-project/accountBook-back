package project.accountBook.config;



import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.accountBook.login.JwtFilter;
import project.accountBook.login.JwtUtil;
import project.accountBook.login.CustomSuccessHandler;
import project.accountBook.login.LogoutFilter;
import project.accountBook.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuthUserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .formLogin(auth -> auth.disable())
                .csrf(auth -> auth.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/api/join", "/api/login", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LogoutFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/accountbook/login")
                        .userInfoEndpoint(
                                userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuthUserService))
                        .successHandler(customSuccessHandler))

                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
