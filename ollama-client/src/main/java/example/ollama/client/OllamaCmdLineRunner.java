package example.ollama.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
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
        LOGGER.info("Calling Ollama ...");
        var response = client
                .prompt("Where in the world is Carmen Sandiego?")
                // .options(opt -> opt.) TODO example!
                .call();
        LOGGER.info(response.content());
    }
}
