# Postman Collection for Spring AI Dynamic Prompts API

## Overview
This Postman collection contains all API endpoints for the Spring AI Dynamic Prompts demo application.

## Import Instructions

1. Open Postman
2. Click **Import** button (top left)
3. Select **File** tab
4. Choose `Spring-AI-Dynamic-Prompts.postman_collection.json`
5. Click **Import**

## Collection Structure

### 1. GET Endpoints (Retrieve Prompt Templates)

#### Get All Prompts
- **Endpoint**: `GET http://localhost:8080/api/prompts`
- **Description**: Retrieves all available prompt templates
- **Response**: Array of prompt objects with id, name, template, and description

#### Get Prompt by ID
- **Endpoint**: `GET http://localhost:8080/api/prompts/{id}`
- **Available IDs**: `greeting`, `explain`, `summarize`, `translate`
- **Description**: Retrieves a specific prompt template details
- **Response**: Single prompt object

### 2. POST Endpoints (Execute Prompts with LLM)

#### Execute Greeting Prompt
- **Endpoint**: `POST http://localhost:8080/api/prompts/greeting/execute`
- **Template**: "Say hello to {name} in a friendly way."
- **Parameters**:
  ```json
  {
    "name": "John"
  }
  ```

#### Execute Explain Prompt
- **Endpoint**: `POST http://localhost:8080/api/prompts/explain/execute`
- **Template**: "Explain {topic} in simple terms that a beginner can understand."
- **Parameters**:
  ```json
  {
    "topic": "quantum computing"
  }
  ```

#### Execute Summarize Prompt
- **Endpoint**: `POST http://localhost:8080/api/prompts/summarize/execute`
- **Template**: "Summarize the following text in {words} words: {text}"
- **Parameters**:
  ```json
  {
    "words": "10",
    "text": "today I woke up in the morning early and went to santa parade. it was very nice and had good time there and back to home by afternoon"
  }
  ```
- **Example Response**:
  ```json
  {
    "promptId": "summarize",
    "response": "Woke up early, enjoyed Santa parade, returned home by afternoon."
  }
  ```

#### Execute Translate Prompt
- **Endpoint**: `POST http://localhost:8080/api/prompts/translate/execute`
- **Template**: "Translate the following text to {language}: {text}"
- **Parameters**:
  ```json
  {
    "language": "Spanish",
    "text": "Hello, how are you today? I hope you are having a wonderful day."
  }
  ```

## Response Format

### Success Response (Execute Endpoints)
```json
{
  "promptId": "string",
  "response": "string (LLM generated response)"
}
```

### Error Responses
- **404 Not Found** - Prompt ID does not exist
  ```json
  {
    "error": "Prompt not found with ID: {id}"
  }
  ```
- **400 Bad Request** - Missing required parameters
  ```json
  {
    "error": "Missing required parameter: {paramName}"
  }
  ```
- **500 Internal Server Error** - LLM or processing error
  ```json
  {
    "error": "Failed to execute prompt: {errorMessage}"
  }
  ```

## Configuration

### Environment Variables
The collection includes a `baseUrl` variable set to `http://localhost:8080`. You can:
1. Change it in the collection variables
2. Or create a Postman environment with different values

### Prerequisites
- Spring Boot application running on `http://localhost:8080`
- OpenAI API key configured in `application.properties`

## Testing Flow

### Recommended Test Sequence:

1. **Get All Prompts** - See what's available
2. **Get Prompt by ID** - Inspect template structure and required parameters
3. **Execute Prompt** - Call LLM with your parameters

### Quick Test (All Execute Endpoints)
Run the "Execute" requests in this order to test all prompt types:
1. Execute Greeting Prompt
2. Execute Explain Prompt
3. Execute Summarize Prompt
4. Execute Translate Prompt

## cURL Equivalents

### Get All Prompts
```bash
curl -X 'GET' \
  'http://localhost:8080/api/prompts' \
  -H 'accept: application/json'
```

### Execute Summarize Prompt
```bash
curl -X 'POST' \
  'http://localhost:8080/api/prompts/summarize/execute' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "words": "10",
  "text": "today I woke up in the morning early and went to santa parade. it was very nice and had good time there and back to home by afternoon"
}'
```

## Notes

- **GET endpoints** retrieve prompt templates only (no LLM call, fast, free)
- **POST execute endpoints** call the LLM (requires API key, takes time, costs money)
- All parameters in templates (marked with `{paramName}`) are required
- The application loads prompts from `src/main/resources/prompts.json`
- Add new prompts by editing `prompts.json` and restarting the app

## Troubleshooting

### Connection Refused
- Ensure Spring Boot application is running: `mvn spring-boot:run`
- Check application is on port 8080: `server.port=8080` in `application.properties`

### 404 Not Found on Execute
- Verify prompt ID exists by calling `GET /api/prompts`
- Check spelling of prompt ID in URL path

### Missing Parameter Error
- Use `GET /api/prompts/{id}` to see the template
- Identify all `{paramName}` placeholders
- Ensure all parameters are provided in request body

### OpenAI API Error
- Verify `spring.ai.openai.api-key` is set in `application.properties`
- Check API key is valid and has credits
- Review application logs for detailed error messages

