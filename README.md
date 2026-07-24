# Building MCP servers with Spring AI

A multi-module Maven project demonstrating how to build and consume an [MCP (Model Context Protocol)](https://modelcontextprotocol.io) server using Spring Boot 4.1.0 and Spring AI 2.0.0 (Java 21).

## Modules

| Module | Description |
|---|---|
| `mcp-server` | Spring Boot app exposing MCP tools and resources via Streamable HTTP on port 8080 |
| `mcp-client` | Headless Spring Boot app with an interactive REPL that calls the server's tools |
| `mcp-server-jira` | Stub module — only a `pom.xml`, no source yet |

## Running

Start the server first, then the client in a separate terminal.
Homebrew's `mvn` uses Java 25, so export a Java 21 `JAVA_HOME` first:

```bash
export JAVA_HOME=/Users/trelle/Library/Java/JavaVirtualMachines/openjdk-26/Contents/Home

# Terminal 1
mvn spring-boot:run -pl mcp-server

# Terminal 2
mvn spring-boot:run -pl mcp-client
```

The client drops into an interactive shell:

```
mcp-client> list-tools
mcp-client> list-resources
mcp-client> create-policy-number MOTOR
mcp-client> help
mcp-client> exit
```

## Adding a new MCP tool

Add a method to `PolicyNumberTools` (or any `@Component` in `example.mcp.server`) and annotate it:

```java
@McpTool(description = "What this tool does")
public String myTool(
        @McpToolParam(description = "...", required = true) String input) {
    ...
}
```

Spring AI generates the JSON schema and registers the tool automatically at startup — no further configuration needed.

## Adding a new MCP resource

Annotate a method in a `@Component` (see `resources.DemoResource`) with:

```java
@McpResource(uri = "...", name = "...", description = "...")
public String myResource() {
    ...
}
```

## Generic MCP Clients

The MCP server exposed by this project is a standard HTTP endpoint and can be reached by any MCP-compatible client, not just the bundled REPL.

### Testing & Debugging

**MCP Inspector** is the fastest way to explore any MCP server interactively. It is the official open-source browser UI from Anthropic:

```bash
npx @modelcontextprotocol/inspector http://localhost:8080/mcp
```

It shows all registered tools and lets you call them directly without writing any client code.

### Desktop Clients (Mac & Windows)

| Client | Open Source | Notes |
|---|---|---|
| [Claude Desktop](https://claude.ai/download) | No (free) | Native MCP support via `claude_desktop_config.json`; connects to local servers |
| [Jan](https://jan.ai) | Yes | Local-first AI desktop client with MCP support |
| [Lobe Chat](https://github.com/lobehub/lobe-chat) | Yes | Web-based chat UI with MCP tool integration |

### IDE Extensions

| Extension | Editor | Notes |
|---|---|---|
| [Continue](https://github.com/continuedev/continue) | VS Code / JetBrains | Open source AI coding assistant, MCP-capable |
| [Zed](https://zed.dev) | Zed | MCP support built into the editor |

> The MCP ecosystem is evolving rapidly. The most current list of compatible clients is maintained at [modelcontextprotocol.io](https://modelcontextprotocol.io).
