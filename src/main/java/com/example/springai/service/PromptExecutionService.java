package com.example.springai.service;

import com.example.springai.model.Prompt;
import com.example.springai.repository.PromptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public PromptExecutionService(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
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
        String response = simulateAIResponse(processedPrompt);
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

    /**
     * Simulate AI response (placeholder for actual AI integration)
     * In production, this would integrate with OpenAI, Anthropic, or other AI services
     */
    private String simulateAIResponse(String processedPrompt) {
        // This is a mock response - in production this would call an actual AI API
        return String.format("AI Response to: '%s'\n\nThis is a simulated response. " +
                "In production, this would be replaced with actual AI integration " +
                "(e.g., Spring AI with OpenAI, Anthropic, etc.)", processedPrompt);
    }

    /**
     * Get all available prompts
     */
    public java.util.List<Prompt> getAllPrompts() {
        return promptRepository.findAll();
    }

    /**
     * Get a specific prompt by ID
     */
    public Optional<Prompt> getPromptById(String id) {
        return promptRepository.findById(id);
    }
}
