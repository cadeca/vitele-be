package ro.cadeca.weasylearn.subjects

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddAndGetSubjectIT : BaseDataIT() {

    private val path = "/api/subject"

    private val mapper = jacksonObjectMapper()

    @BeforeAll
    fun prepareDatabase() {
        addSubject(SubjectDTO("subj 1 e2", "code1", "description1", 1))
        addSubject(SubjectDTO("subj2", "code2"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can query a subject by name containing or code containing`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", "e2")).andReturn().response.contentAsString)
        assertEquals(2, subjects.size)
    }

    @Test
    @WithMockKeycloakAuth(TEACHER, STUDENT)
    fun `teachers or students cannot add subjects`() {
        mockMvc().perform(post(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(SubjectDTO("name", "code", "description", 3)))).andExpect(status().isForbidden)
    }

    private fun addSubject(subjectDTO: SubjectDTO) {
        mockMvc().with(keycloakAuthenticationToken().authorities(ADMIN))
                .perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(subjectDTO)))
    }
}
