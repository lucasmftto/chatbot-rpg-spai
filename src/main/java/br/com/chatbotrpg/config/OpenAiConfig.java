package br.com.chatbotrpg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Value("${spring.ai.vectorstore.pinecone.apiKey}")
    private String pineconeApiKey;

    @Value("${spring.ai.vectorstore.pinecone.index-name}")
    private String indexName;

    @Value("${spring.ai.vectorstore.pinecone.projectId}")
    private String projectId;

    @Value("${spring.ai.vectorstore.pinecone.environment}")
    private String environment;

}
