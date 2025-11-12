# spring-ai-demo-02-dynamic-prompts

Spring AI API with dynamic prompt templates

## Overview

This Spring Boot application demonstrates a dynamic prompt management system with the following components:

- **PromptController**: REST API endpoints for managing and executing prompts
- **PromptExecutionService**: Service layer for executing prompts with parameter substitution
- **PromptRepository**: Repository for loading and managing prompts from JSON
- **Prompt Model**: Entity class representing a prompt template

## Features

- Load prompts dynamically from `prompts.json`
- REST API for listing and retrieving prompts
- Execute prompts with dynamic parameter substitution
- Error handling for missing prompts and parameters
- Extensible architecture ready for AI integration (OpenAI, Anthropic, etc.)

## API Endpoints

### Get All Prompts
```
GET /api/prompts
```

Returns a list of all available prompts.

### Get Prompt by ID
```
GET /api/prompts/{id}
```

Returns a specific prompt by its ID.

### Execute Prompt
```
POST /api/prompts/{id}/execute
Content-Type: application/json

{
  "parameter1": "value1",
  "parameter2": "value2"
}
```

Executes a prompt with the provided parameters.

## Example Usage

### List all prompts:
```bash
curl http://localhost:8080/api/prompts
```

### Get a specific prompt:
```bash
curl http://localhost:8080/api/prompts/greeting
```

### Execute a prompt:
```bash
curl -X POST http://localhost:8080/api/prompts/greeting/execute \
  -H "Content-Type: application/json" \
  -d '{"name": "Alice"}'
```

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project:
```bash
mvn clean compile
```

### Run the application:
```bash
mvn spring-boot:run
```

The application will start on port 8080.

## Project Structure

```
src/
├── main/
│   ├── java/com/example/springai/
│   │   ├── SpringAiDemoApplication.java    # Main application class
│   │   ├── controller/
│   │   │   └── PromptController.java       # REST API controller
│   │   ├── service/
│   │   │   └── PromptExecutionService.java # Business logic for prompt execution
│   │   ├── repository/
│   │   │   └── PromptRepository.java       # Data access layer for prompts
│   │   └── model/
│   │       └── Prompt.java                 # Prompt entity model
│   └── resources/
│       ├── application.properties          # Application configuration
│       └── prompts.json                    # Prompt templates
```

## Prompt Configuration

Prompts are defined in `src/main/resources/prompts.json`:

```json
[
  {
    "id": "greeting",
    "name": "Greeting Prompt",
    "template": "Say hello to {name} in a friendly way.",
    "description": "Generates a friendly greeting"
  }
]
```

Parameters in the template are enclosed in curly braces `{parameter}` and will be replaced with actual values during execution.

## Future Enhancements

- Integration with Spring AI for actual AI model execution
- Support for OpenAI, Anthropic, and other AI providers
- Prompt versioning and management
- User authentication and authorization
- Rate limiting and caching
