# ollama-client

A minimal Spring Boot application that sends a prompt to a locally running
[Ollama](https://ollama.com) model via [Spring AI](https://spring.io/projects/spring-ai)
and prints the answer.

## Prerequisites

- Java 21
- [Ollama](https://ollama.com) installed locally

## Install and start Ollama

```bash
# Install (macOS)
brew install ollama

# Start the Ollama background service (serves on http://localhost:11434)
ollama serve
```

## Pull the model

The application is configured to use the `qwen3.6` model (see
`src/main/resources/application.yml`). Pull it once — it stays cached locally afterwards:

```bash
ollama pull qwen3.6
```

You can verify the model is available and try it directly:

```bash
ollama list
ollama run qwen3.6 "Where in the world is Carmen Sandiego?"
```

> To use a different model, change `spring.ai.ollama.chat.options.model` in
> `application.yml` and pull the corresponding model with `ollama pull <model>`.

## Run the application

Homebrew's `mvn` may default to a newer JDK, so export a Java 21 `JAVA_HOME` first:

```bash
export JAVA_HOME=/path/to/java-21

# Run from the reactor root
mvn spring-boot:run -pl ollama-client
```

On startup the application sends the prompt and logs the model's reply:

```
INFO  --- OllamaCmdLineRunner : Calling Ollama ...
INFO  --- OllamaCmdLineRunner : Carmen Sandiego is a fictional character ...
```

## Configuration

Relevant settings in `src/main/resources/application.yml`:

| Property | Description |
|---|---|
| `spring.ai.ollama.base-url` | URL of the local Ollama server (default `http://localhost:11434`) |
| `spring.ai.ollama.chat.options.model` | Model used for chat requests (`qwen3.6`) |
| `spring.ai.ollama.init.pull-model-strategy` | `never` — assumes the model is already pulled. Set to `when_missing` to let Spring AI pull it automatically |
