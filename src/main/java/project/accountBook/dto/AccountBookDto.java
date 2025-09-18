package project.accountBook.dto;


import lombok.Data;
import project.accountBook.entity.CategoryType;

import java.util.Map;

@Data
public class AccountBookDto {

    private int year;
    private int month;
    private long totalIncome;
    private long totalExpense;

    private Map<CategoryType, Long> stat;

    public AccountBookDto(int year, int month, long totalIncome, long totalExpense, Map<CategoryType, Long> stat) {
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.stat = stat;
    }
}
