package ro.cadeca.weasylearn.subjects

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ro.cadeca.weasylearn.*
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetUsersSubjectsIT : BaseDataIT() {

    private val path = "/api/subject"

    private val mapper = jacksonObjectMapper()

    @BeforeAll
    fun `add users to subjects`() {
        subjectRepository.save(subject1.apply { teacher = teacher1.username })
        subjectRepository.save(subject2.apply {
            teacher = teacher2.username
            tutors = setOf(teacher1.username)
            students = setOf(student1.username)
        })
    }

    @Test
    fun `get subjects of teacher1`() {
        val subjects: List<SubjectDTO> = mockMvc().with(mockAuth(teacher1.username, TEACHER))
                .get(path).andReturn().response.contentAsString
                .let(mapper::readValue)

        assertEquals(2, subjects.size)
        assertThat(subjects.map { it.code }, containsInAnyOrder(subject1.code, subject2.code))
    }

    @Test
    fun `get subject of teacher2`() {
        testUserWith1Subject(teacher2.username, TEACHER, subject2.code)
    }

    @Test
    fun `get subject of student1`() {
        testUserWith1Subject(student1.username, STUDENT, subject2.code)
    }

    private fun testUserWith1Subject(username: String, role: String, code: String) {
        val subjects: List<SubjectDTO> = mockMvc().with(mockAuth(username, role))
                .get(path).andReturn().response.contentAsString
                .let(mapper::readValue)

        assertEquals(1, subjects.size)
        assertEquals(code, subjects.first().code)
    }
}
