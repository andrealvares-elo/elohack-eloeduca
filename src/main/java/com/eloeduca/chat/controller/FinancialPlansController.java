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
                "Gere um plano financeiro para que esse usuário possa atingir seus objetivos financeiros. " +
                "Crie a evolução do plano até que ele atinja a proporção correta de 50/30/20. " +
                "Gere a resposta no formato JSON conforme exemplo: \n" +
                " " +
                "{" + "\"shortTermGoal\": \"Pagar dívidas\", " +
                "\"longTermGoal\": \"Comprar um imovel\", " +
                "\"planEvolutions\": [" +
                "{\"month\": \"\", \"year\": \"2024\", " +
                "\"goalDescription\": \"Diminuir despesas não essenciais em 20%. Aumentar os investimentos em 5%.\", " +
                "\"totalIncome\": 1500.0, \"totalExpense\": 2500.0, \"totalEssentialExpense\": 1500.0, \"totalNonEssentialExpense\": 1000.0,  \"totalInvestment\": 0.0}]}" + "\n" +
                " " +
                "Não faça comentários adicionais. Apenas gere o plano financeiro.";

        var response = this.chatClient
                .prompt()
                .user(prompt)
                .call()
                .chatResponse();

        var plan = FinancialPlan.fromJson(response.getResult().getOutput().getContent());
        plan.setCurrentFinanceOverview(financeSummary);

        return plan;
    }
}
