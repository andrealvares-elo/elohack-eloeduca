package com.eloeduca.chat.controller;


import com.eloeduca.chat.client.ChatAiClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

  private final ChatAiClient chatAiClient;

  public ChatController(ChatAiClient chatAiClient) {
    this.chatAiClient = chatAiClient;
  }

  @PostMapping("/ask")
  public PromptResponse ask(@RequestBody PromptRequest questionRequest) {
    return new PromptResponse(chatAiClient.chat(questionRequest.question()));
  }

}
