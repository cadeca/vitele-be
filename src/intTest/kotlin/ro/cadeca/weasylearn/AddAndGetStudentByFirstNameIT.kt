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

class AddAndGetStudentByFirstNameIT : PostgresIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/user"

    private val mapper = jacksonObjectMapper()

  /*  @Test
    @WithMockKeycloakAuth(ADMIN, TEACHER)
    fun `i can add an user to the database and then get it from the list of users by his first name`() {
        mockMvc.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Student("Doe5", "John5", 2, "1.1", emptyList(), "Politehnica", "AC", "CTI"))))
        val usersByFirstName: List<Student> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/allByFirstName").param("firstName", "John5")).andReturn().response.contentAsString)

        val user = usersByFirstName.find { it.lastName == "Doe5" } !!
        Assertions.assertEquals("John5", user.firstName)
    }*/
}
