package org.dsu.eventlog.domain.model.common;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Embeddable
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class NameDescription {

    private static final int NAME_SIZE = 200;
    private static final int DESCRIPTION_SIZE = 4000;

    @Column(name = "name", nullable = false, length = NAME_SIZE)
    @Getter
    private String name;

    @Column(name = "description", nullable = true, length = DESCRIPTION_SIZE)
    @Getter
    private String description;

    public NameDescription(String name, String description) {
        setName(name);
        setDescription(description);
    }

    private void setName(String name) {
        checkNotNull(name, "The argument 'name' must not be null.");
        checkArgument(StringUtils.isNotBlank(name), "The argument 'name' must not be empty.");
        checkArgument(name.trim().length() <= NAME_SIZE,
                String.format("The maximum size of 'name' is limited by %d.", NAME_SIZE));

        this.name = name.trim();
    }

    private void setDescription(String description) {
        if (StringUtils.isBlank(description)) {
            return;
        }

        checkArgument(description.trim().length() <= DESCRIPTION_SIZE,
                String.format("The maximum size of 'description' is limited by %d.", DESCRIPTION_SIZE));

        this.description = description.trim();
    }
}
