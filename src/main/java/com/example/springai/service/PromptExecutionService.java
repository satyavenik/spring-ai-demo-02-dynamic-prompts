package com.example.springai.service;

import com.example.springai.model.Prompt;
import com.example.springai.repository.PromptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service to execute prompts with dynamic parameter substitution
 */
@Service
public class PromptExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(PromptExecutionService.class);
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    private final PromptRepository promptRepository;
    private final ChatClient chatClient;

    public PromptExecutionService(PromptRepository promptRepository, ChatClient chatClient) {
        this.promptRepository = promptRepository;
        this.chatClient = chatClient;
    }

    /**
     * Execute a prompt by ID with given parameters
     */
    public String executePrompt(String promptId, Map<String, Object> parameters) {
        logger.debug("Executing prompt with ID: {}", promptId);

        Optional<Prompt> promptOpt = promptRepository.findById(promptId);
        if (promptOpt.isEmpty()) {
            throw new IllegalArgumentException("Prompt not found with ID: " + promptId);
        }

        Prompt prompt = promptOpt.get();
        logger.debug("Found prompt: {}", prompt);

        // Replace placeholders in the template with actual parameters
        String processedPrompt = replaceParameters(prompt.getTemplate(), parameters);
        logger.debug("Processed prompt: {}", processedPrompt);

        // Simulate AI response - In real implementation, this would call an AI service
        String response = callOpenAI(processedPrompt);
        logger.debug("Prompt execution completed successfully");

        return response;
    }

    /**
     * Replace parameters in the template string
     */
    private String replaceParameters(String template, Map<String, Object> parameters) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = PARAMETER_PATTERN.matcher(template);

        while (matcher.find()) {
            String paramName = matcher.group(1);
            Object paramValue = parameters.get(paramName);
            if (paramValue == null) {
                throw new IllegalArgumentException("Missing required parameter: " + paramName);
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(paramValue.toString()));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private String callOpenAI(String processedPrompt) {
        return chatClient.prompt()
                .user(processedPrompt)
                .call()
                .content();
    }

    /**
     * Get all available prompts (templates only, no LLM call)
     * Use this to browse available prompts and see their structure
     */
    public java.util.List<Prompt> getAllPrompts() {
        return promptRepository.findAll();
    }

    /**
     * Get a prompt template by ID (no LLM call)
     * Use this to inspect the template structure and required parameters
     * before calling executePrompt()
     */
    public Optional<Prompt> getPromptById(String id) {
        return promptRepository.findById(id);
    }
}