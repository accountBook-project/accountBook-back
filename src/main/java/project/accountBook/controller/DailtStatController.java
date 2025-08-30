package project.accountBook.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.service.DailyStatService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DailtStatController {

    private final DailyStatService dailyStatService;

    @GetMapping("/user/dailystat")
    public ResponseEntity<?> dailyStat(@AuthenticationPrincipal(expression = "userId") String userId,
                                       @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        DailyStatDto dailyStat = dailyStatService.findDailyStat(Long.valueOf(userId), localDate);
        return ResponseEntity.ok(dailyStat);
    }
}
