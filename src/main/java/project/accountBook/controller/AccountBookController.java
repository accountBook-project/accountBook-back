package project.accountBook.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.accountBook.dto.AccountBookDto;
import project.accountBook.entity.DailyStatType;
import project.accountBook.service.AccountBookService;

@RestController
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/user/accountbook/{type}")
    public ResponseEntity<?> accountBookMonth(@AuthenticationPrincipal(expression = "userId") String userId,
                                              @PathVariable(value = "type") String type,
                                              @RequestParam int year,
                                              @RequestParam int month) {
        DailyStatType dailyStatType = Enum.valueOf(DailyStatType.class, type.toUpperCase());
        AccountBookDto accountBookDto = accountBookService.findMonthData(Long.valueOf(userId), year, month, dailyStatType);
        return ResponseEntity.ok(accountBookDto);
    }
}
