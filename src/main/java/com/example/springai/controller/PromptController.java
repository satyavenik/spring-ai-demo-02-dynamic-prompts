package com.example.springai.controller;

import com.example.springai.model.Prompt;
import com.example.springai.service.PromptExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for prompt management and execution
 */
@RestController
@RequestMapping("/api/prompts")
public class PromptController {
    
    private static final Logger logger = LoggerFactory.getLogger(PromptController.class);
    
    private final PromptExecutionService promptExecutionService;

    public PromptController(PromptExecutionService promptExecutionService) {
        this.promptExecutionService = promptExecutionService;
    }

    /**
     * Get all available prompts
     */
    @GetMapping
    public ResponseEntity<List<Prompt>> getAllPrompts() {
        logger.info("GET /api/prompts - Fetching all prompts");
        List<Prompt> prompts = promptExecutionService.getAllPrompts();
        return ResponseEntity.ok(prompts);
    }

    /**
     * Get a specific prompt by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prompt> getPromptById(@PathVariable String id) {
        logger.info("GET /api/prompts/{} - Fetching prompt by ID", id);
        return promptExecutionService.getPromptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Execute a prompt with parameters
     */
    @PostMapping("/{id}/execute")
    public ResponseEntity<?> executePrompt(
            @PathVariable String id,
            @RequestBody Map<String, Object> parameters) {
        logger.info("POST /api/prompts/{}/execute - Executing prompt with parameters", id);
        
        try {
            String response = promptExecutionService.executePrompt(id, parameters);
            return ResponseEntity.ok(Map.of(
                    "promptId", id,
                    "response", response
            ));
        } catch (IllegalArgumentException e) {
            logger.error("Error executing prompt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error executing prompt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to execute prompt: " + e.getMessage()));
        }
    }
}
