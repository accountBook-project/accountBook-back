package project.accountBook;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.CustomUserPrincipal;
import project.accountBook.dto.JoinRequest;
import project.accountBook.dto.LoginRequest;
import project.accountBook.dto.UserDto;
import project.accountBook.entity.*;
import project.accountBook.repository.*;
import project.accountBook.service.UserService;

import java.awt.print.Book;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EmotionRepository emotionRepository;
        private final CategoryRepository categoryRepository;
        private final PaymentRepository paymentRepository;

        public void dbInit1() {
            emotionRepository.save(new Emotion(EmotionType.슬픔));
            emotionRepository.save(new Emotion(EmotionType.기분전환));
            emotionRepository.save(new Emotion(EmotionType.스트레스));
            emotionRepository.save(new Emotion(EmotionType.충동구매));
            emotionRepository.save(new Emotion(EmotionType.flex));
            emotionRepository.save(new Emotion(EmotionType.소확행));
            categoryRepository.save(new Category(CategoryType.고정지출));
            categoryRepository.save(new Category(CategoryType.건강));
            categoryRepository.save(new Category(CategoryType.식비));
            categoryRepository.save(new Category(CategoryType.경조사));
            categoryRepository.save(new Category(CategoryType.교육));
            categoryRepository.save(new Category(CategoryType.기타));
            categoryRepository.save(new Category(CategoryType.도서));
            categoryRepository.save(new Category(CategoryType.문화생활));
            categoryRepository.save(new Category(CategoryType.미용));
            categoryRepository.save(new Category(CategoryType.부업));
            categoryRepository.save(new Category(CategoryType.생필품));
            categoryRepository.save(new Category(CategoryType.선물));
            categoryRepository.save(new Category(CategoryType.쇼핑));
            categoryRepository.save(new Category(CategoryType.용돈));
            categoryRepository.save(new Category(CategoryType.월급));
            categoryRepository.save(new Category(CategoryType.의료비));
            categoryRepository.save(new Category(CategoryType.카페));
            categoryRepository.save(new Category(CategoryType.교통비));
            paymentRepository.save(new Payment(PaymentType.상품권));
            paymentRepository.save(new Payment(PaymentType.계좌이체));
            paymentRepository.save(new Payment(PaymentType.기타));
            paymentRepository.save(new Payment(PaymentType.신용카드));
            paymentRepository.save(new Payment(PaymentType.체크카드));
            paymentRepository.save(new Payment(PaymentType.현금));

        }

    }
}