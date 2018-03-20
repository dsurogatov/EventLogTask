package org.dsu.eventlog.domain.model.common

import static org.apache.commons.lang3.RandomStringUtils.random

import org.apache.commons.lang3.RandomStringUtils
import org.dsu.eventlog.domain.model.common.NameDescription

class NameDescriptionSpec extends spock.lang.Specification {

	def "Should create the NameDescription trimming spaces in input arguments."() {
		setup:
		def nameDescription = new NameDescription(name, description)
		
		expect:
		nameDescription.name == expectedName
		nameDescription.description == expectedDescription
		
		where:
		name     | description     | expectedName | expectedDescription
		"name"   | "description"   | "name"       | "description"
		"name"   | ""              | "name"       | null
		"name"   | "      "        | "name"       | null
		"name "  | "description "  | "name"       | "description"
		" name"  | " description"  | "name"       | "description"
		" name " | " description " | "name"       | "description"
	}
	
	def "Should throw the exception when passed wrong name."() {
		when:
		new NameDescription(name, "description")
		
		then:
		Exception exception = thrown()
		expectedException.isInstance(exception)
		exception.message == message
		
        where:
        name        | expectedException        | message
        null        | NullPointerException     | "The argument 'name' must not be null."
        ""          | IllegalArgumentException | "The argument 'name' must not be empty."
        "  "        | IllegalArgumentException | "The argument 'name' must not be empty."
		random(201) | IllegalArgumentException | "The maximum size of 'name' is limited by 200."
	}
	
	def "Should throw the exception when passed long description."() {
		when:
		new NameDescription("name", description)
		
		then:
		Exception exception = thrown()
		expectedException.isInstance(exception)
		exception.message == message
		
		where:
		description  | expectedException        | message
		random(4001) | IllegalArgumentException | "The maximum size of 'description' is limited by 4000."
	}
}
