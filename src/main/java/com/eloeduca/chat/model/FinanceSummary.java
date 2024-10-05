package com.eloeduca.chat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.List;

@Data
@Builder
public class FinanceSummary {

    private Double totalIncome;
    private Double totalExpense;
    private Double totalEssentialExpense;
    private Double totalNonEssentialExpense;
    private List<Finance> transactions;

    @SneakyThrows
    public String toJson() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
