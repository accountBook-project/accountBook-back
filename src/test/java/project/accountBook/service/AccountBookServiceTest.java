package project.accountBook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.AccountBookDto;
import project.accountBook.dto.CustomUserPrincipal;
import project.accountBook.dto.UserDto;
import project.accountBook.entity.*;
import project.accountBook.repository.AccountBookRepository;
import project.accountBook.repository.CategoryRepository;
import project.accountBook.repository.DailyStatRepository;
import project.accountBook.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AccountBookServiceTest {

    @Autowired
    AccountBookRepository accountBookRepository;
    @Autowired
    DailyStatRepository dailyStatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AccountBookService accountBookService;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void 테스트_데이터_삽입() throws Exception {
        User user1 = new User("sangjae", "abc123", "ROLE_USER");
        User user2 = new User("suyeon", "abc123", "ROLE_USER");
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        AccountBook accountBook1 = new AccountBook(savedUser1, 2025, 9, 1000, 12000);
        AccountBook accountBook2 = new AccountBook(savedUser2, 2025, 11, 300, 3000);
        accountBookRepository.save(accountBook1);
        accountBookRepository.save(accountBook2);
        Category category1 = new Category(CategoryType.식비);
        Category category2 = new Category(CategoryType.용돈);
        Category category3 = new Category(CategoryType.생필품);
        Category category4 = new Category(CategoryType.의료비);
        Category savedCategory1 = categoryRepository.save(category1);
        Category savedCategory2 = categoryRepository.save(category2);
        Category savedCategory3 = categoryRepository.save(category3);
        Category savedCategory4 = categoryRepository.save(category4);

        DailyStat dailyStat1 = new DailyStat(LocalDate.of(2025, 9, 3), 3000L, "화가 나서 샀습니다",
                DailyStatType.EXPENSE, savedUser1);
        DailyStat dailyStat2 = new DailyStat(LocalDate.of(2025, 9, 4), 3000L, "짜증이 나서 샀습니다",
                DailyStatType.EXPENSE, savedUser1);
        DailyStat dailyStat3 = new DailyStat(LocalDate.of(2025, 9, 5), 3000L, "기분이 좋아서 샀습니다",
                DailyStatType.EXPENSE, savedUser1);
        DailyStat dailyStat4 = new DailyStat(LocalDate.of(2025, 9, 5), 3000L, "슬퍼서 샀습니다",
                DailyStatType.EXPENSE, savedUser1);
        DailyStat dailyStat5 = new DailyStat(LocalDate.of(2025, 9, 3), 1000L, "안녕하세요",
                DailyStatType.INCOME, savedUser2);
        dailyStat1.addCategory(savedCategory1);
        dailyStat2.addCategory(savedCategory2);
        dailyStat3.addCategory(savedCategory3);
        dailyStat4.addCategory(savedCategory3);
        dailyStat5.addCategory(savedCategory4);
        dailyStatRepository.save(dailyStat1);
        dailyStatRepository.save(dailyStat2);
        dailyStatRepository.save(dailyStat3);
        dailyStatRepository.save(dailyStat4);
        dailyStatRepository.save(dailyStat5);
    }
    @Test
    public void 통계_테스트() throws Exception {
        //given
        Optional<User> user = userRepository.findByUsername("sangjae");
        //when
        AccountBookDto accountBookDto = accountBookService.findMonthData(user.get().getId(), 2025, 9, DailyStatType.EXPENSE);

        System.out.println(accountBookDto);

        //then
        assertThat(accountBookDto.getTotalExpense()).isEqualTo(12000);
        assertThat(accountBookDto.getTotalIncome()).isEqualTo(1000);
        assertThat(accountBookDto.getYear()).isEqualTo(2025);
        assertThat(accountBookDto.getMonth()).isEqualTo(9);
        assertThat(accountBookDto.getStat().size()).isEqualTo(3);
    }

    @Test
    public void 통계_MockMvc_테스트() throws Exception {
        //given
        User user = userRepository.findByUsername("sangjae").get();

        CustomUserPrincipal authUser = new CustomUserPrincipal(new UserDto(user.getId().toString(), user.getUsername(), "ROLE_USER"));
        Authentication token = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        //when
        SecurityContextHolder.getContext().setAuthentication(token);



        //then
        mockMvc.perform(get("/user/accountbook/{type}", DailyStatType.EXPENSE)
                        .param("year", "2025")
                        .param("month", "9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").value(1000))
                .andExpect(jsonPath("$.totalExpense").value(12000))
                .andExpect(jsonPath("$.year").value(2025))
                .andExpect(jsonPath("$.month").value(9))
                .andExpect(jsonPath("$.stat.식비").value(3000));
    }

}