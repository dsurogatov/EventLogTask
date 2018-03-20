package org.dsu.eventlog.domain.model.eventlogitem;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "eventlogitem_intvalue")
@ToString(callSuper = true)
@NoArgsConstructor
public class EventLogItemIntValue extends AbstractEventLogItem<Integer> {

    public EventLogItemIntValue(int value) {
        super(value);
    }

}
