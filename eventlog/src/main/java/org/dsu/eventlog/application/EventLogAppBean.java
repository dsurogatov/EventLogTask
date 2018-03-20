package org.dsu.eventlog.application;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dsu.eventlog.application.dto.RefDto;
import org.dsu.eventlog.domain.model.common.NameDescription;
import org.dsu.eventlog.domain.model.eventlog.AddEventLogCommand;
import org.dsu.eventlog.domain.model.eventlog.EventLog;
import org.dsu.eventlog.domain.model.eventlog.EventLogId;
import org.dsu.eventlog.domain.model.eventlog.EventLogRepository;
import org.dsu.eventlog.domain.model.eventlog.RemoveEventLogCommand;
import org.dsu.eventlog.domain.model.eventlog.UpdateEventLogCommand;
import org.dsu.eventlog.infrastructura.command.CommandExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EventLogAppBean {

    private final CommandExecutor commandExecutor;
    private final EventLogRepository eventLogRepository;

    public long create(String name, String description) {
        @SuppressWarnings("unchecked")
        Optional<EventLogId> eventLogIdOpt = (Optional<EventLogId>) commandExecutor
                .execute(new AddEventLogCommand(name, description));

        return eventLogIdOpt.get().id();
    }

    public void update(RefDto eventLogRef) {
        checkNotNull(eventLogRef, argNotBeNull("eventLogRef"));

        commandExecutor.execute(new UpdateEventLogCommand(EventLogId.of(eventLogRef.id()),
                new NameDescription(eventLogRef.name(), eventLogRef.description())));
    }

    public void remove(long id) {
        commandExecutor.execute(new RemoveEventLogCommand(EventLogId.of(id)));
    }

    @Transactional
    public List<RefDto> list() {
        return eventLogRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public RefDto one(long id) {
        Optional<EventLog> eventLogOpt = eventLogRepository.findById(id);
        Preconditions.checkState(eventLogOpt.isPresent(), "There is no the EventLog with id=" + id);
        return toDto(eventLogOpt.get());
    }

    private RefDto toDto(EventLog e) {
        return new RefDto().id(e.id()).name(e.name()).description(e.description());
    }

}
