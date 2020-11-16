package ro.cadeca.weasylearn.subjects

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO

class GetSubjectIT : BaseDataIT() {

    private val path = "/api/subject/search"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can query a subject by name containing or code containing as admin`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", "e2")).andReturn().response.contentAsString)
        assertEquals(2, subjects.size)
    }

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `i can query a subject by name containing or code containing as teacher`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", "2")).andReturn().response.contentAsString)
        assertEquals(2, subjects.size)
    }

    @Test
    @WithMockKeycloakAuth(STUDENT)
    fun `i can query a subject by name containing or code containing as student`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", "subj2")).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
    }
}
