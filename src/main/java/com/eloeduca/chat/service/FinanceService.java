package com.eloeduca.chat.service;

import com.eloeduca.chat.model.Finance;
import com.eloeduca.chat.model.FinanceSummary;
import com.eloeduca.chat.repository.FinanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;

    public FinanceSummary getFinanceSummary() {
        var transactions = financeRepository.findAll();

        var totalIncome = transactions.stream()
                .filter(finance -> finance.getType() == Finance.Type.INCOME)
                .mapToDouble(Finance::getAmount)
                .sum();

        var totalExpense = transactions.stream()
                .filter(finance -> finance.getType() == Finance.Type.EXPENSE)
                .mapToDouble(Finance::getAmount)
                .sum();

        var totalEssentialExpense = transactions.stream()
                .filter(finance -> finance.getType() == Finance.Type.EXPENSE)
                .filter(finance -> finance.getExpenseBucket() != null && finance.getExpenseBucket() == Finance.ExpenseBucket.ESSENTIAL)
                .mapToDouble(Finance::getAmount)
                .sum();

        var totalNonEssentialExpense = transactions.stream()
                .filter(finance -> finance.getType() == Finance.Type.EXPENSE)
                .filter(finance -> finance.getExpenseBucket() != null && finance.getExpenseBucket() == Finance.ExpenseBucket.NON_ESSENTIAL)
                .mapToDouble(Finance::getAmount)
                .sum();

        return FinanceSummary.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .totalEssentialExpense(totalEssentialExpense)
                .totalNonEssentialExpense(totalNonEssentialExpense)
                .transactions(transactions)
                .build();
    }
}
