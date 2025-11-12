package com.example.springai.repository;

import com.example.springai.model.Prompt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PromptRepository
 */
@SpringBootTest
class PromptRepositoryTest {

    @Autowired
    private PromptRepository promptRepository;

    @Test
    void testLoadPrompts() {
        List<Prompt> prompts = promptRepository.findAll();
        assertNotNull(prompts);
        assertEquals(4, prompts.size());
    }

    @Test
    void testFindById() {
        Optional<Prompt> prompt = promptRepository.findById("greeting");
        assertTrue(prompt.isPresent());
        assertEquals("greeting", prompt.get().getId());
        assertEquals("Greeting Prompt", prompt.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Prompt> prompt = promptRepository.findById("nonexistent");
        assertFalse(prompt.isPresent());
    }

    @Test
    void testExistsById() {
        assertTrue(promptRepository.existsById("greeting"));
        assertFalse(promptRepository.existsById("nonexistent"));
    }
}
