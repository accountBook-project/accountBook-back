//package project.accountBook;
//
//
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import project.accountBook.dto.CustomUserPrincipal;
//import project.accountBook.dto.JoinRequest;
//import project.accountBook.dto.LoginRequest;
//import project.accountBook.dto.UserDto;
//import project.accountBook.entity.*;
//import project.accountBook.repository.AccountBookRepository;
//import project.accountBook.repository.CategoryRepository;
//import project.accountBook.repository.DailyStatRepository;
//import project.accountBook.repository.UserRepository;
//import project.accountBook.service.UserService;
//
//import java.awt.print.Book;
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class InitData {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final UserRepository userRepository;
//        private final AccountBookRepository accountBookRepository;
//        private final CategoryRepository categoryRepository;
//        private final DailyStatRepository dailyStatRepository;
//        private final UserService userService;
//
//        public void dbInit1() {
//            User user1 = new User("sangjae", "abc123", "ROLE_USER");
//            User user2 = new User("suyeon", "abc123", "ROLE_USER");
//            User savedUser1 = userRepository.save(user1);
//            User savedUser2 = userRepository.save(user2);
//            AccountBook accountBook1 = new AccountBook(savedUser1, 2025, 9, 1000, 12000);
//            AccountBook accountBook2 = new AccountBook(savedUser2, 2025, 11, 300, 3000);
//            accountBookRepository.save(accountBook1);
//            accountBookRepository.save(accountBook2);
//            Category category1 = new Category(CategoryType.A);
//            Category category2 = new Category(CategoryType.B);
//            Category category3 = new Category(CategoryType.C);
//            Category category4 = new Category(CategoryType.D);
//            Category savedCategory1 = categoryRepository.save(category1);
//            Category savedCategory2 = categoryRepository.save(category2);
//            Category savedCategory3 = categoryRepository.save(category3);
//            Category savedCategory4 = categoryRepository.save(category4);
//
//            DailyStat dailyStat1 = new DailyStat(LocalDate.of(2025, 9, 3), 3000L, "화가 나서 샀습니다",
//                    DailyStatType.EXPENSE, savedUser1);
//            DailyStat dailyStat2 = new DailyStat(LocalDate.of(2025, 9, 4), 3000L, "짜증이 나서 샀습니다",
//                    DailyStatType.EXPENSE, savedUser1);
//            DailyStat dailyStat3 = new DailyStat(LocalDate.of(2025, 9, 5), 3000L, "기분이 좋아서 샀습니다",
//                    DailyStatType.EXPENSE, savedUser1);
//            DailyStat dailyStat4 = new DailyStat(LocalDate.of(2025, 9, 5), 3000L, "슬퍼서 샀습니다",
//                    DailyStatType.EXPENSE, savedUser1);
//            DailyStat dailyStat5 = new DailyStat(LocalDate.of(2025, 9, 3), 1000L, "안녕하세요",
//                    DailyStatType.INCOME, savedUser2);
//            dailyStat1.addCategory(savedCategory1);
//            dailyStat2.addCategory(savedCategory2);
//            dailyStat3.addCategory(savedCategory3);
//            dailyStat4.addCategory(savedCategory3);
//            dailyStat5.addCategory(savedCategory4);
//            dailyStatRepository.save(dailyStat1);
//            dailyStatRepository.save(dailyStat2);
//            dailyStatRepository.save(dailyStat3);
//            dailyStatRepository.save(dailyStat4);
//            dailyStatRepository.save(dailyStat5);
//
//            User user = userRepository.findByUsername("sangjae").get();
//
//            CustomUserPrincipal authUser = new CustomUserPrincipal(new UserDto(user.getId().toString(), user.getUsername(), "ROLE_USER"));
//            Authentication token = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
//            //when
//            SecurityContextHolder.getContext().setAuthentication(token);
//
//        }
//
//    }
//}
//
