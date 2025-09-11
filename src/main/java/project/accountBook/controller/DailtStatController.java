package project.accountBook.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.service.DailyStatService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DailtStatController {

    private final DailyStatService dailyStatService;

    @GetMapping("/dailystat/get")
    public ResponseEntity<?> dailyStat(@AuthenticationPrincipal(expression = "userId") String userId,
                                       @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        DailyStatDto dailyStatDto = dailyStatService.findDailyStat(Long.valueOf(userId), localDate);
        return ResponseEntity.ok(dailyStatDto);
    }
    @PostMapping("/dailystat/transaction")
    public ResponseEntity<?> dailyStatCreate(@AuthenticationPrincipal(expression = "userId") String userId,
                                             @RequestBody DailyStatDto dailyStatDto) {
        try {
            dailyStatService.save(dailyStatDto, userId);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("저장 중 오류 발생");
        }
        return ResponseEntity.ok(dailyStatDto);
    }
}
