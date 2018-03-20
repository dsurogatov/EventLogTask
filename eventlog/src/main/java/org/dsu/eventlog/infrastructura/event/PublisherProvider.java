package org.dsu.eventlog.infrastructura.event;

import org.dsu.eventlog.domain.event.EventPublisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class PublisherProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static EventPublisher eventPublisher() {
        return context == null ? null : context.getBean(EventPublisher.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
