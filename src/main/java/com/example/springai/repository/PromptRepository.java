package com.example.springai.repository;

import com.example.springai.model.Prompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository to load and manage prompts from prompts.json
 */
@Repository
public class PromptRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(PromptRepository.class);
    private static final String PROMPTS_FILE = "prompts.json";
    
    private final ObjectMapper objectMapper;
    private List<Prompt> prompts;

    public PromptRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.prompts = new ArrayList<>();
    }

    /**
     * Load prompts from prompts.json on initialization
     */
    @PostConstruct
    public void loadPrompts() {
        try {
            ClassPathResource resource = new ClassPathResource(PROMPTS_FILE);
            InputStream inputStream = resource.getInputStream();
            prompts = objectMapper.readValue(inputStream, new TypeReference<List<Prompt>>() {});
            logger.info("Successfully loaded {} prompts from {}", prompts.size(), PROMPTS_FILE);
        } catch (IOException e) {
            logger.error("Failed to load prompts from {}", PROMPTS_FILE, e);
            prompts = new ArrayList<>();
        }
    }

    /**
     * Get all prompts
     */
    public List<Prompt> findAll() {
        return new ArrayList<>(prompts);
    }

    /**
     * Find a prompt by its ID
     */
    public Optional<Prompt> findById(String id) {
        return prompts.stream()
                .filter(prompt -> prompt.getId().equals(id))
                .findFirst();
    }

    /**
     * Check if a prompt exists
     */
    public boolean existsById(String id) {
        return prompts.stream()
                .anyMatch(prompt -> prompt.getId().equals(id));
    }
}
