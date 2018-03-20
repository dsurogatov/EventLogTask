package org.dsu.eventlog.infrastructura.repository;

import static java.lang.String.format;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dsu.eventlog.domain.model.common.BoxId;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@AllArgsConstructor
class HibernateEntityRepository implements EntityRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private Session session() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public <T> void add(T entity) {
        checkNotNull(entity, argNotBeNull("entity"));
        session().saveOrUpdate(entity);
    }

    @Override
    public <T> void update(T entity) {
        checkNotNull(entity, argNotBeNull("entity"));
        session().saveOrUpdate(entity);
    }

    @Override
    public <T> void remove(T entity) {
        checkNotNull(entity, argNotBeNull("entity"));
        session().delete(entity);
    }

    @Override
    public void flush() {
        session().flush();
    }

    @Override
    public <T, ID> T findRequired(Class<T> clazz, BoxId<ID> boxId) {
        checkNotNull(clazz, argNotBeNull("clazz"));
        checkNotNull(boxId, argNotBeNull("boxId"));
        checkNotNull(boxId.id(), argNotBeNull("boxId.id"));

        T t = session().find(clazz, boxId.id());
        checkNotNull(t, format("The entity '%s' was not fount by id '%d'.", clazz.getSimpleName(), boxId.id()));
        return t;
    }
}
