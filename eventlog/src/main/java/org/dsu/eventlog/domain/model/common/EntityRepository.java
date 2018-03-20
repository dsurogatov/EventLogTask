package org.dsu.eventlog.domain.model.common;

public interface EntityRepository {

    <T> void add(T entity);
    <T> void update(T entity);
    <T> void remove(T entity);
    void flush();
    <T, ID> T findRequired(Class<T> clazz, BoxId<ID> boxId);
}
