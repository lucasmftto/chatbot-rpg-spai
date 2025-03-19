package br.com.chatbotrpg.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private OpenAiChatModel openAiChatClient;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private OpenAiChatModel chatModel;

    public String callChatbotFirstImplementation(String message) {

        List<Document> documents = this.vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(message)
                        .similarityThresholdAll()
                        .topK(5).build());

        var systemMessage = "Responda apenas com base nos documentos{documents} fornecido. ";

        var systemTemplate = new SystemPromptTemplate(systemMessage)
                .createMessage(Map.of("documents", documents));

        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemTemplate, userMessage));
        ChatResponse chatResponse = this.openAiChatClient.call(prompt);

        return chatResponse.getResults().stream()
                .map(generation -> generation.getOutput().getText())
                .collect(Collectors.joining("/n"));

    }

    public String callChatbot(String message) {
        ChatResponse chatResponse = ChatClient.builder(this.chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(this.vectorStore))
                .user(message)
                .call()
                .chatResponse();

        return chatResponse.getResults().stream()
                .map(generation -> generation.getOutput().getText())
                .collect(Collectors.joining("/n"));
    }
}
