package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.STUDENT
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.Subject

@AutoConfigureMockMvc
class AddAndGetSubjectIT : PostgresIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/subject"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `i can add a subject to the database and then get it from the list of subjects`() {
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Subject("name", "code", "description", 3))))
        val subjects: List<Subject> = mapper.readValue(mockMvc.perform(get(path)).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
        assertEquals("code", subjects.first().code)
    }

    @Test
    @WithMockKeycloakAuth(TEACHER, STUDENT)
    fun `teachers or students cannot add subjects`() {
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Subject("name", "code", "description", 3)))).andExpect(status().isForbidden)
    }
}
