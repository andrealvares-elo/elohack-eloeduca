package com.eloeduca.chat.controller;

import com.eloeduca.chat.service.FinanceService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("insights")
public class InsightsController {

  private final FinanceService financeService;
  private final ChatClient chatClient;

  public InsightsController(FinanceService financeService, ChatModel chatModel) {
    this.financeService = financeService;
    this.chatClient = ChatClient
            .builder(chatModel)
            .defaultSystem("Você é um assistente financeiro. Você ajuda pessoas dando dicas sobre comportamentos " +
                    "que podem melhorar a saúde financeira das pessoas. Suas dicas tem no máximo 50 palavras. " +
                    "Suas dicas tem bom humor.")
            .build();
  }

  @GetMapping
  public PromptResponse getInsight() {

    var financeSummary = financeService.getFinanceSummary();

    var prompt = "As movimentações financeiras do usuário estão listadas abaixo em formato JSON:\n" +
            financeSummary.toJson() + "\n" +
            "Gere uma dica financeira para o usuário com base nas movimentações financeiras listadas.";

    var response = this.chatClient
            .prompt()
            .user(prompt)
            .call()
            .chatResponse().getResult();

    return new PromptResponse(response.getOutput().getContent());
  }

}
