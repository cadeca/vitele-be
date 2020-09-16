package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.model.User

class AddAndGetUserIT : BasePostgreSQLContainerIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/users"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(ADMIN, TEACHER)
    fun `i can add an user to the database and then get it from the list of users`() {
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(User("Doe", "John", 2, "1.1", emptyList(), "Politehnica", "AC", "CTI"))))
        val users: List<User> = mapper.readValue(mockMvc.perform(get(path)).andReturn().response.contentAsString)

        Assertions.assertEquals(1, users.size)
        Assertions.assertEquals("Doe", users.first().lastName)
        Assertions.assertEquals("John", users.first().firstName)
        Assertions.assertEquals(2, users.first().year)
        Assertions.assertEquals("1.1", users.first().groupId)
        Assertions.assertEquals("Politehnica", users.first().university)
        Assertions.assertEquals("AC", users.first().faculty)
        Assertions.assertEquals("CTI", users.first().department)
    }
}
