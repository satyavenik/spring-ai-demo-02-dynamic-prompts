package com.example.springai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for ChatClient bean.
 * This uses Spring AI's auto-configured ChatClient.Builder to create a ChatClient instance.
 * The builder is automatically configured with the OpenAI ChatModel when available.
 */
@Configuration
public class ChatClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(ChatClientConfig.class);

    /**
     * Create a ChatClient bean from the auto-configured ChatClient.Builder.
     * Spring AI auto-configuration provides the Builder with the OpenAI ChatModel wired in.
     */
    @Bean
    @ConditionalOnMissingBean(ChatClient.class)
    public ChatClient chatClient(@Autowired(required = false) ChatClient.Builder chatClientBuilder) {
        if (chatClientBuilder != null) {
            logger.info("Creating ChatClient from auto-configured ChatClient.Builder");
            return chatClientBuilder.build();
        } else {
            logger.warn("No ChatClient.Builder found - ChatClient bean creation will fail. " +
                    "Ensure spring.ai.openai.api-key is configured or OpenAI auto-configuration is enabled.");
            throw new IllegalStateException("ChatClient.Builder not available. " +
                    "Please configure spring.ai.openai.api-key in application.properties");
        }
    }
}

