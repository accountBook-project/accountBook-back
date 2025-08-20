package project.accountBook.oauth2_jwt;


import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import project.accountBook.jwt.JwtUtil;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2JwtTest {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void jwwCreateTest() throws Exception {
        //given
        String token = jwtUtil.createJwt("namekey", "ROLE_USER", 10 * 10 * 10L);
        //when

        //then
        assertThat(jwtUtil.getUserKey(token)).isEqualTo("namekey");
        assertThat(jwtUtil.getRole(token)).isEqualTo("ROLE_USER");
        assertThat(jwtUtil.isExpired(token)).isFalse();
    }
    
    @Test
    public void oAuthLoginTest() throws Exception {
        //given
        String token = jwtUtil.createJwt("namekey", "ROLE_USER", 10 * 10 * 10L);
        //when
        
        //then
        mockMvc.perform(get("/oauth2/authorization/naver"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/my")
                .cookie(new Cookie("Authorization", token)))
                .andExpect(status().isOk());
    }

}
