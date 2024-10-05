package com.eloeduca.chat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "finance")
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Type type;
    private String description;
    private Double amount;
    private ExpenseBucket expenseBucket;

    public static Finance of(Type type, String description, Double amount) {
        var finance = new Finance();
        finance.setType(type);
        finance.setDescription(description);
        finance.setAmount(amount);
        return finance;
    }

    public enum Type {
        INCOME , EXPENSE, INVESTMENT;
    }

    public enum ExpenseBucket {
        ESSENTIAL, NON_ESSENTIAL;
    }
}
