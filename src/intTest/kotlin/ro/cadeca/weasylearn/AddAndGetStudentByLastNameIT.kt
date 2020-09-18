package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.Student

class AddAndGetStudentByLastNameIT : PostgresIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/users"

    private val mapper = jacksonObjectMapper()
/*
    @Test
    @WithMockKeycloakAuth(ADMIN, TEACHER)
    fun `i can add an user to the database and then get it from the list of users by his last name`() {
        mockMvc.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Student("Doe2", "John", 2, "1.1", emptyList(), "Politehnica", "AC", "CTI"))))
        val usersByLastName: List<Student> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/allByLastName").param("lastName", "Doe2")).andReturn().response.contentAsString)

        val user = usersByLastName.find { it.lastName == "Doe2" } !!
        Assertions.assertEquals("Doe2", user.lastName)
    }  */
}
