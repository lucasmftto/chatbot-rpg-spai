package br.com.chatbotrpg.controller;

import br.com.chatbotrpg.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
public class ChatbotController {

    @Autowired
    private ChatbotService service;

    @GetMapping("/chatbot")
    public ResponseEntity<String> model(
            @RequestParam(value = "message", defaultValue = "Hello") String message) {

        return ResponseEntity.ok(this.service.callChatbot(message));
    }

    @PostMapping("/load-file")
    public ResponseEntity<Void> loadFile(
            @RequestParam("file") MultipartFile file) throws IOException {

        this.service.loadFiles(file);
        return ResponseEntity.ok().build();
    }
}
