package com.eloeduca.chat.controller;

import com.eloeduca.chat.model.FinancialPlan;
import com.eloeduca.chat.service.FinanceService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financial-plans")
public class FinancialPlansController {

    private final FinanceService financeService;

    private final ChatClient chatClient;

    public FinancialPlansController(FinanceService financeService, ChatModel chatModel) {
        this.financeService = financeService;
        this.chatClient = ChatClient.builder(chatModel)
                .defaultSystem("Você é um assistente financeiro. Você cria planos financeiros baseados nos objetivos de uma pessoa." +
                        "Você utiliza a regra 50/30/20 para criar os planos financeiros. 50% da renda é para necessidades básicas." +
                        "30% da renda é para gastos não essenciais. E 20% da renda é destinada para investimentos.")
                .build();
    }

    @GetMapping
    public FinancialPlan getFinancialPlan(@RequestParam("shortTermGoal") String shortTermGoal,
                                          @RequestParam("longTermGoal") String longTermGoal) {
        var financeSummary = financeService.getFinanceSummary();

        var prompt = "Objetivo de curto prazo: " + shortTermGoal + "\n" +
                "Objetivo de longo prazo: " + longTermGoal + "\n" +
                "As movimentações financeiras do usuário estão listadas abaixo em formato JSON:" + "\n" +
                financeSummary.toJson() + "\n" +
                "Gere um plano financeiro de 3 meses para que esse usuário possa atingir seus objetivos financeiros." +
                "Gere a resposta no formato JSON conforme exemplo: {" +
                "\"shortTermGoal\": \"\", " +
                "\"longTermGoal\": \"\", " +
                "\"planEvolutions\": [" +
                "{\"month\": 1, " +
                "\"year\": 2021, " +
                "\"goalDescription\": \"\", " +
                "\"totalIncome\": 0.0, " +
                "\"totalExpense\": 0.0, " +
                "\"totalEssentialExpense\": 0.0, " +
                "\"totalNonEssentialExpense\": 0.0, " +
                "\"totalInvestment\": 0.0}" +
                "]}" + "\n" +
                "Não faça comentários adicionais. Apenas gere o plano financeiro.";

        var response = this.chatClient
                .prompt()
                .user(prompt)
                .call()
                .chatResponse();

        return FinancialPlan.fromJson(response.getResult().getOutput().getContent());
    }
}
