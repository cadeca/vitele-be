package ro.cadeca.weasylearn.subjects

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ro.cadeca.weasylearn.*
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.dto.UserDTO
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO

class UpdateSubjectIT : BaseDataIT() {

    private val path = "/api/subject"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `get subject and update it to change teacher tutors and students`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get("$path/search").param("query", " 1")).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
        val subject = SubjectSaveDTO(
                name = subjects.first().name,
                code = subjects.first().code,
                id = subjects.first().id,
                teacher = teacher1.username,
                students = listOf(student1.username, student2.username),
                tutors = listOf(student1.username, teacher1.username)
        )

        mockMvc().perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(subject)))

        val newSubjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get("$path/search").param("query", " 1")).andReturn().response.contentAsString)
        val newSubject = newSubjects.first()

        assertEquals("code1", newSubject.code)
        assertEquals(teacher1.email, newSubject.teacher?.email)
        assertEquals(2, newSubject.students?.size)
        assertTrue(newSubject.students?.map { it.email }?.containsAll(setOf(student1.email, student2.email))
                ?: fail("Students is null"))
        assertEquals(2, newSubject.tutors?.size)
        assertTrue(newSubject.tutors?.map { it.email }?.containsAll(setOf(student1.email, teacher1.email))
                ?: fail("Tutors is null"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `get subject and update it to change description and add semester`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get("$path/search").param("query", " 1")).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
        val subject = subjects.first().let {
            SubjectSaveDTO(
                    id = it.id,
                    name = it.name,
                    code = it.code,
                    description = "new description",
                    semester = 2,
                    teacher = it.teacher?.username,
                    students = it.students?.map(UserDTO::username),
                    tutors = it.tutors?.map(UserDTO::username)
            )
        }

        mockMvc().perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(subject)))

        val newSubjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get("$path/search").param("query", " 1")).andReturn().response.contentAsString)
        val newSubject = newSubjects.first()

        assertEquals("code1", newSubject.code)
        assertEquals("new description", newSubject.description)
        assertEquals(2, newSubject.semester)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add teacher to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/teacher").param("username", "berlin"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertEquals("berlin.fonollosa@aut.upt.ro", subject.teacher?.email)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add tutors to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/tutor").param("username", "berlin"))
        mockMvc().perform(put("$path/$subjectId/tutor").param("username", "johnDoe"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertTrue(subject.tutors?.map { it.email }?.containsAll(listOf("berlin.fonollosa@aut.upt.ro", "john.doe@student.upt.ro"))
                ?: fail("tutors field is nul"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add students to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/student").param("username", "JohnSnow"))
        mockMvc().perform(put("$path/$subjectId/student").param("username", "johnDoe"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertTrue(subject.students?.map { it.email }?.containsAll(listOf("john.snow@student.upt.ro", "john.doe@student.upt.ro"))
                ?: fail("students field is nul"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `remove tutors from subject`() {
        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        val subjectId = subject.id
        mockMvc().perform(delete("$path/$subjectId/tutor").param("username", teacher2.username))
        val subjectAfter = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        assertEquals(arrayListOf(user1.username), subjectAfter.tutors?.map { it.username })
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `remove invalid tutor from subject`() {
        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        val subjectId = subject.id
        mockMvc().perform(delete("$path/$subjectId/tutor").param("username", "invalid")).andExpect(status().is4xxClientError)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `remove students from subject`() {
        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        val subjectId = subject.id
        mockMvc().perform(delete("$path/$subjectId/student").param("username", student2.username))
        val subjectAfter = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        assertEquals(arrayListOf(student1.username), subjectAfter.students?.map { it.username })
    }


    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `remove invalid student from subject`() {
        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get("$path/search").param("query", " 3"))
                .andReturn().response.contentAsString).first()
        val subjectId = subject.id
        mockMvc().perform(delete("$path/$subjectId/student").param("username", "invalid"))
    }
}
