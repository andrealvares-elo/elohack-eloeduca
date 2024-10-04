package com.eloeduca.chat.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@Component
public class ChatAiClient {

  private static final Integer CHAT_ID = 1;
  private static final Logger log = LoggerFactory.getLogger(ChatAiClient.class);

  private final ChatModel chatModel;
  private ChatClient chatClient;
  private final ChatMemory chatMemory;

  public ChatAiClient(ChatModel chatModel, ChatMemory chatMemory) {
    this.chatModel = chatModel;
    this.chatMemory = chatMemory;
    this.chatClient = ChatClient
        .builder(chatModel)
        .defaultSystem("Você é um sistema especialista em assuntos financeiros, "
            + "que ajuda com dúvidas financeiras e na organização de gastos, despesas e receitas.")
        .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
        .build();
  }

  public String chat(String userPrompt) {
    log.info("chat - question: {}", userPrompt);

    var response = this.chatClient.prompt()
        .user(userPrompt)
        .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, CHAT_ID))
        .call()
        .chatResponse()
        .getResult();

    var res = response.getOutput().getContent();
    log.info("chat - question answered: {}", res);

    return res;
  }
}