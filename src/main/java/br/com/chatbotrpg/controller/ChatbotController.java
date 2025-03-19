package br.com.chatbotrpg.controller;

import br.com.chatbotrpg.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ChatbotController {

    @Autowired
    private ChatbotService service;

    @GetMapping("/chatbot")
    public ResponseEntity<String> model(@RequestParam(value = "message", defaultValue = "Hello") String message) {

        return  ResponseEntity.ok(this.service.callChatbot(message));
    }
}
