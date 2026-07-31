package example.ollama.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Simple command line runner that calls Ollama.
 * @author Tobias Trelle
 */
@Component
public class OllamaCmdLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OllamaCmdLineRunner.class);

    private final ChatClient client;

    public OllamaCmdLineRunner(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @Override
    public void run(String... args) {
        String prompt = args.length > 0
                ? String.join(" ", args)
                : "Where in the world is Carmen Sandiego?";
        LOGGER.info("Calling Ollama ...");
        var response = client
                .prompt(prompt)
                .options(OllamaChatOptions.builder()
                        .temperature(0.7))
                .call();
        LOGGER.info(response.content());
    }
}
