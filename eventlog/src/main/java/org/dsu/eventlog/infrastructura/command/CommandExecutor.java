package org.dsu.eventlog.infrastructura.command;

import org.dsu.eventlog.domain.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommandExecutor {

    @Autowired
    private RunEnvironment runEnvironment;

    @Transactional
    public Object execute(Command command) {
        return runEnvironment.run(command);
    }
}
