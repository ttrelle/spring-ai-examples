package example.mcp.client;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service for interacting with the MCP server via a {@link McpSyncClient}.
 *
 * @author Tobias Trelle, codecentric AG
 */
@Service
public class McpClientService {

    private final McpSyncClient mcpClient;

    public McpClientService(List<McpSyncClient> mcpClients) {
        this.mcpClient = mcpClients.getFirst();
    }

    public List<McpSchema.Tool> listTools() {
        return mcpClient.listTools().tools();
    }

    public List<McpSchema.Resource> listResources() {
        return mcpClient.listResources().resources();
    }

    public String callTool(String toolName, Map<String, Object> arguments) {
        McpSchema.CallToolResult result = mcpClient.callTool(
                new McpSchema.CallToolRequest(toolName, arguments)
        );
        return result.content().stream()
                .filter(c -> c instanceof McpSchema.TextContent)
                .map(c -> ((McpSchema.TextContent) c).text())
                .findFirst()
                .orElse("");
    }
}
