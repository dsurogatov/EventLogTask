package org.dsu.eventlog.infrastructura.repository

import javax.persistence.EntityManager

import org.dsu.eventlog.domain.model.common.AbstractEntity
import org.dsu.eventlog.domain.model.common.BoxId
import org.hibernate.Session

import spock.lang.Specification

class HibernateEntityRepositorySpec extends Specification {

	EntityManager entityManager = Mock()
	Session session = Mock()
	HibernateEntityRepository repository

	def setup() {
		entityManager.unwrap(_) >> session
		repository = new HibernateEntityRepository(entityManager)
	}

	def "Test the 'add' method."() {
		when:
		repository.add(_)

		then:
		1 * session.saveOrUpdate(_)

		when: 'pass null argument to the add method'
		repository.add(null)

		then:
		def exception = thrown(NullPointerException)
        exception.message == "The argument 'entity' must not be null."
		0 * session.saveOrUpdate(_)
	}
    
    def "Test the 'update' method."() {
        when:
        repository.update(_)

        then:
        1 * session.saveOrUpdate(_)

        when:
        repository.update(null)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "The argument 'entity' must not be null."
        0 * session.saveOrUpdate(_)
    }

	def "Test the 'remove' method."() {
		when:
		repository.remove(_)

		then:
		1 * session.delete(_)

		when:
		repository.remove(null)

		then:
		def exception = thrown(NullPointerException)
        exception.message == "The argument 'entity' must not be null."
		0 * session.delete(_)
	}

	def "Test the 'flush' method."() {
		when:
		repository.flush()

		then:
		1 * session.flush()
	}

	def "Test the 'findRequired' method."() {
		when:
		def ret = repository.findRequired(Object.class, id(1L))

		then:
		ret != null
		1 * session.find(Object.class, 1L) >> new Object()

		when:
		repository.findRequired(Object.class, id(2L))

		then:
		NullPointerException exception = thrown()
		exception.message == "The entity 'Object' was not fount by id '2'."
		1 * session.find(Object.class, 2L)

		when:
		repository.findRequired(null, id(3L))

		then:
		exception = thrown()
		exception.message == "The argument 'clazz' must not be null."
		0 * session.find(null, 3L)

		when:
		repository.findRequired(Object.class, null)

		then:
		exception = thrown()
		exception.message == "The argument 'boxId' must not be null."
		0 * session.find(Object.class, null)

		when:
		repository.findRequired(Object.class, Mock(BoxId))

		then:
		exception = thrown()
		exception.message == "The argument 'boxId.id' must not be null."
		0 * session._
	}

	private id(long longId) {
		def id = Mock(BoxId)
		id.id() >> longId
		id
	}
}
