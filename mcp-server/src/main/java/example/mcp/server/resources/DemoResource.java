package example.mcp.server.resources;

import org.springframework.ai.mcp.annotation.McpResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * MCP resource exposing a demo text file from the classpath.
 *
 * @author Tobias Trelle, codecentric AG
 */
@Component
public class DemoResource {

    @McpResource(
            uri = "file:///demo.txt",
            name = "demo-text",
            description = "Demo text resource loaded from the classpath"
    )
    public String demoText() {
        try {
            return new ClassPathResource("demo.txt")
                    .getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read classpath resource demo.txt", e);
        }
    }
}
