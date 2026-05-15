package example.mcp.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Interactive REPL for calling MCP tools from the command line.
 *
 * @author Tobias Trelle, codecentric AG
 */
@Component
public class McpShellCommands implements CommandLineRunner {

    private final McpClientService client;

    public McpShellCommands(McpClientService client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        printHelp();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nmcp-client> ");
                System.out.flush();
                if (!scanner.hasNextLine()) break;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                if ("exit".equals(line) || "quit".equals(line)) break;
                handleCommand(line);
            }
        }
        System.out.println("Bye.");
    }

    private void handleCommand(String line) {
        String[] parts = line.split("\\s+", 2);
        String cmd = parts[0];
        String arg = parts.length > 1 ? parts[1].trim() : "";
        try {
            switch (cmd) {
                case "list-tools"      -> System.out.println(listTools());
                case "list-resources"  -> System.out.println(listResources());
                case "create-policy-number" -> System.out.println(createPolicyNumber(arg));
                case "help"        -> printHelp();
                default            -> System.out.println("Unknown command '" + cmd + "'. Type 'help'.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String listTools() {
        return client.listTools().stream()
                .map(t -> String.format("  %-25s %s", t.name(), t.description()))
                .collect(Collectors.joining("\n"));
    }

    private String listResources() {
        return client.listResources().stream()
                .map(r -> String.format("  %-35s %s", r.uri(), r.description()))
                .collect(Collectors.joining("\n"));
    }

    private String createPolicyNumber(String lobId) {
        if (StringUtils.isEmpty(lobId)) return "Usage: create-policy-number <lobId>";
        return client.callTool("createPolicyNumber", Map.of("lobId", lobId));
    }

    private void printHelp() {
        System.out.println("""
                MCP Client
                ────────────────────────────────────────────────────────────
                list-tools                      List available MCP tools
                list-resources                  List available MCP resources
                create-policy-number <lobId>    Create a new policy number       
                help                            Show this help
                exit / quit                     Exit
                ────────────────────────────────────────────────────────────""");
    }
}
