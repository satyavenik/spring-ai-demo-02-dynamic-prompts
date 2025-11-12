package com.example.springai.service;

import com.example.springai.model.Prompt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PromptExecutionService
 */
@SpringBootTest
class PromptExecutionServiceTest {

    @Autowired
    private PromptExecutionService promptExecutionService;

    @Test
    void testGetAllPrompts() {
        List<Prompt> prompts = promptExecutionService.getAllPrompts();
        assertNotNull(prompts);
        assertEquals(4, prompts.size());
    }

    @Test
    void testGetPromptById() {
        Optional<Prompt> prompt = promptExecutionService.getPromptById("greeting");
        assertTrue(prompt.isPresent());
        assertEquals("greeting", prompt.get().getId());
    }

    @Test
    void testExecutePrompt() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Alice");
        
        String response = promptExecutionService.executePrompt("greeting", parameters);
        assertNotNull(response);
        assertTrue(response.contains("Say hello to Alice in a friendly way"));
    }

    @Test
    void testExecutePromptWithMissingParameter() {
        Map<String, Object> parameters = new HashMap<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            promptExecutionService.executePrompt("greeting", parameters);
        });
    }

    @Test
    void testExecutePromptWithInvalidId() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Alice");
        
        assertThrows(IllegalArgumentException.class, () -> {
            promptExecutionService.executePrompt("nonexistent", parameters);
        });
    }
}
