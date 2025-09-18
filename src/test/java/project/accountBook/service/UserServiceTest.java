package project.accountBook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.JoinRequest;
import project.accountBook.dto.LoginRequest;
import project.accountBook.entity.User;
import project.accountBook.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    
    
    @Test
    public void 회원가입_테스트() throws Exception {
        //given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("sjsj0718");
        joinRequest.setPw("abcdefg123123!");

        //when
        Long join = userService.join(joinRequest);
        User user = userRepository.findById(join).orElse(null);
        //then
        Assertions.assertThat(join).isEqualTo(user.getId());
    }
    @Test
    public void 회원가입_MockMvc_테스트() throws Exception {
        //given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("sjsj0718");
        joinRequest.setPw("abcdefg123123!");
        joinRequest.setCfpw("abcdefg123123!");
        //when
//        mockMvc.perform(post("/api/join")
//                .param("username", joinRequest.getUsername())
//                .param("pw", joinRequest.getPw())
//                .param("cfpw", joinRequest.getCfpw()))
//                .andExpect(status().isOk());
        String json = objectMapper.writeValueAsString(joinRequest);
        mockMvc.perform(post("/api/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        //then
    }

    @Test
    public void 로그인_MockMvc_테스트() throws Exception {
        //given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("sjsj0718");
        joinRequest.setPw("abcdefg123123!");
        userService.join(joinRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("sjsj0718");
        loginRequest.setPw("abcdefg123123!");

        String json = objectMapper.writeValueAsString(loginRequest);
        //when
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Set-Cookie", Matchers.hasItem(Matchers.containsString("access="))));

        //then
    }
}