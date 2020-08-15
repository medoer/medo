package medo.framework.message.command.consumer;

import java.util.Collections;
import java.util.Map;

import medo.framework.message.command.common.Command;
import medo.framework.message.command.common.paths.ResourcePathPattern;

public class CommandWithDestinationBuilder {

    private Command command;
    private String destinationChannel;
    private String resource;
    private Map<String, String> extraHeaders = Collections.emptyMap();

    public CommandWithDestinationBuilder(Command command) {
        this.command = command;
    }

    public static CommandWithDestinationBuilder send(Command command) {
        return new CommandWithDestinationBuilder(command);
    }

    public CommandWithDestinationBuilder to(String destinationChannel) {
        this.destinationChannel = destinationChannel;
        return this;
    }

    public CommandWithDestinationBuilder forResource(String resource, Object... pathParams) {
        this.resource = new ResourcePathPattern(resource).replacePlaceholders(pathParams).toPath();
        return this;
    }

    public CommandWithDestinationBuilder withExtraHeaders(Map<String, String> headers) {
        this.extraHeaders = headers;
        return this;

    }

    public CommandWithDestination build() {
        return new CommandWithDestination(destinationChannel, resource, command, extraHeaders);
    }

}
