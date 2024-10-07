package com.eloeduca.chat.controller;


import com.eloeduca.chat.client.ChatAiClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@CrossOrigin(origins = "*")
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
