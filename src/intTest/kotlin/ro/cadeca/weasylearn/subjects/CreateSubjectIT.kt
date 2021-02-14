package ro.cadeca.weasylearn.subjects

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import ro.cadeca.weasylearn.student1
import ro.cadeca.weasylearn.teacher1
import ro.cadeca.weasylearn.user1

class CreateSubjectIT : BaseDataIT() {
    private val mapper = jacksonObjectMapper()
    private val path = "/api/subject";

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject as admin`() {
        val tutor = user1.username
        val listOfTutors = listOf(tutor)
        val student = student1.username
        val listOfStudents = listOf(student)
        val s = SubjectSaveDTO("Test", "42", "Test", 2, teacher1.username, listOfTutors, listOfStudents)
        val subject: SubjectEntity = mapper.readValue(mockMvc()
                .perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s)))
                .andReturn().response.contentAsString)
        assertEquals(s.name, subject.name)
        assertEquals(s.code, subject.code)
        assertEquals(s.description, subject.description)
        assertEquals(s.semester, subject.semester)
        assertEquals(s.teacher, subject.teacher)
        assertEquals(s.tutors?.size, subject.tutors?.size)
        assertEquals(s.students?.size, subject.students?.size)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i cannot create a subject with an invalid teacher`() {
        val s = SubjectSaveDTO("Test", "42", null, null, "invalid")
        mockMvc()
                .perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s)))
                .andExpect(status().is4xxClientError)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i cannot create a subject with invalid tutors`() {
        val listOfTutors = listOf("invalid")
        val s = SubjectSaveDTO("Test", "42", null, null, null, listOfTutors)
        mockMvc()
                .perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s)))
                .andExpect(status().is4xxClientError)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i cannot create a subject with invalid students`() {
        val listOfStudents = listOf("invalid")
        val s = SubjectSaveDTO("Test", "42", null, null, null, null, listOfStudents)
        mockMvc()
                .perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s)))
                .andExpect(status().is4xxClientError)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject without a description`() {
        val tutor = user1.username
        val listOfTutors = listOf(tutor)
        val student = student1.username
        val listOfStudents = listOf(student)
        val s = SubjectSaveDTO("Test", "42", null, 2, teacher1.username, listOfTutors, listOfStudents)
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject without a semester`() {
        val tutor = user1.username
        val listOfTutors = listOf(tutor)
        val student = student1.username
        val listOfStudents = listOf(student)
        val s = SubjectSaveDTO("Test", "42", "test", null, teacher1.username, listOfTutors, listOfStudents)
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject without a teacher`() {
        val tutor = user1.username
        val listOfTutors = listOf(tutor)
        val student = student1.username
        val listOfStudents = listOf(student)
        val s = SubjectSaveDTO("Test", "42", "test", 2, null, listOfTutors, listOfStudents)
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject without tutors`() {
        val student = student1.username
        val listOfStudents = listOf(student)
        val s = SubjectSaveDTO("Test", "42", "test", 2, teacher1.username, null, listOfStudents)
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can create a subject without students`() {
        val tutor = user1.username
        val listOfTutors = listOf(tutor)
        val s = SubjectSaveDTO("Test", "42", "test", 2, teacher1.username, listOfTutors, null)
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `i cannot create a subject as teacher`() {
        val s = Subject("Test", "42")
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is4xxClientError)
    }

    @Test
    @WithMockKeycloakAuth(STUDENT)
    fun `i cannot create a subject as student`() {
        val s = Subject("Test", "42")
        mockMvc().perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().is4xxClientError)
    }
}
