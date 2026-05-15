package example.mcp.server.tools;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MCP tool that creates a new policy number
 *
 * @author Tobias Trelle, codecentric AG
 */
@Component
public class PolicyNumberTools {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @McpTool(description = "Creates a new policy number")
    public String createPolicyNumber(
            @McpToolParam(description = "Id of the LOB (line of business)", required = true)
            String lobId) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String randomPart = RandomStringUtils.randomAlphanumeric(5);
        return (lobId + timestamp + randomPart).toUpperCase();
    }

}
