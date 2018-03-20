package org.dsu.eventlog.application;

import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.domain.model.eventlog.EventLog;
import org.dsu.eventlog.domain.model.eventlog.EventLogRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Profile("dev")
public class BootstrapAppBean implements InitializingBean {

    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    @Autowired
    private EventLogRepository eventLogRepository;

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                if (eventLogRepository.findAll().isEmpty()) {
                    entityRepository.add(new EventLog("default", "default"));
                }
            }
        });
    }

}
