package org.dsu.eventlog.domain.model.eventlog;

import org.dsu.eventlog.domain.command.AbstractCommand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class AddEventLogCommand extends AbstractCommand {

    private final String name;
    private final String description;

}
