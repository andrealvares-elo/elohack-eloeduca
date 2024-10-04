package com.eloeduca.chat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
//@Entity(name = "finance")
public class Finance {

    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Type type;
    private String description;
    private Double amount;

    public static Finance of(Type type, String description, Double amount) {
        var finance = new Finance();
        finance.setType(type);
        finance.setDescription(description);
        finance.setAmount(amount);
        return finance;
    }

    public enum Type {
        INCOME , EXPENSE;

        public static Optional<Type> identifyType(String text, String[] incomeKeywords, String[] expenseKeywords) {
            for (String keyword : incomeKeywords) {
                if (text.toLowerCase().contains(keyword)) {
                    return Optional.of(Type.INCOME);
                }
            }

            for (String keyword : expenseKeywords) {
                if (text.toLowerCase().contains(keyword)) {
                    return Optional.of(Type.EXPENSE);
                }
            }

            return Optional.empty();
        }
    }




}
