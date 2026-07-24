package example.ollama.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ollama client application entry point.
 * @author Tobias Trelle
 */
@SpringBootApplication
public class OllamaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OllamaClientApplication.class, args);
    }
}
