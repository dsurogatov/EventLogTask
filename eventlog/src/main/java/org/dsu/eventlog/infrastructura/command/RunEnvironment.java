package org.dsu.eventlog.infrastructura.command;

import org.dsu.eventlog.domain.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RunEnvironment {

    @Autowired
    private SpringHandlersProvider handlersProfiver;

    public Object run(Object command) {
        CommandHandler<Object, Object> handler = handlersProfiver.getHandler(command);
        Object result = handler.handle(command);
        return result;
    }

}
