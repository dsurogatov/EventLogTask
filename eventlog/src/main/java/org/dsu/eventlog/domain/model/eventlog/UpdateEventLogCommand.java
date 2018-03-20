package org.dsu.eventlog.domain.model.eventlog;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import org.dsu.eventlog.domain.command.AbstractCommand;
import org.dsu.eventlog.domain.model.common.NameDescription;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class UpdateEventLogCommand extends AbstractCommand {

    private final EventLogId eventLogId;
    private final NameDescription nameDescription;

    public UpdateEventLogCommand(EventLogId eventLogId, NameDescription nameDescription) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        checkNotNull(nameDescription, argNotBeNull("nameDescription"));

        this.eventLogId = eventLogId;
        this.nameDescription = nameDescription;
    }
}
