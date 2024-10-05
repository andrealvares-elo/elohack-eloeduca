package com.eloeduca.chat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Data
public class FinancialPlan {

    private FinanceSummary currentFinanceOverview;

    private String shortTermGoal;

    private String longTermGoal;

    private List<FinanceEvolution> planEvolutions;

    public FinancialPlan(){}

    @SneakyThrows
    public static FinancialPlan fromJson(String json) {
        return new ObjectMapper().readValue(json, FinancialPlan.class);
    }

    @Data
    public static class FinanceEvolution {
        private int month;
        private int year;
        private String goalDescription;
        private Double totalIncome;
        private Double totalExpense;
        private Double totalEssentialExpense;
        private Double totalNonEssentialExpense;
        private Double totalInvestment;
    }
}
