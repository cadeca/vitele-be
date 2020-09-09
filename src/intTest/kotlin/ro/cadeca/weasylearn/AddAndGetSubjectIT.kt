package ro.cadeca.weasylearn

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import ro.cadeca.weasylearn.model.Subject


class AddAndGetSubjectIT : BasePostgreSQLContainerIT() {
	@LocalServerPort
	private var port: Int = 0

	@Autowired
	private lateinit var testRestTemplate: TestRestTemplate

	private val path = "/api/subject"

	@Test
	fun `i can add a subject to the database and then get it from the list of subjects`() {
		testRestTemplate.put("http://localhost:$port$path", Subject("name", "code", "description", 3))
		val subjects = testRestTemplate.exchange<List<Subject>>("$path/all", HttpMethod.GET).body
				?: fail { "Body is null" }
		assertEquals(1, subjects.size)
		assertEquals("code", subjects.first().code)
	}
}