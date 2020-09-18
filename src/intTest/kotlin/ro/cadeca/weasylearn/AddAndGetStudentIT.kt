package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.User
import java.util.*

class AddAndGetStudentIT : PostgresIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/users"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(ADMIN, TEACHER)
    fun `i can add an user to the database and then get it from the list of users`() {
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Student("johnDoe", "John", "Doe", Date(1/1/1990), byteArrayOf(), "john.doe@gmail.com", "bachelor",2, "1.2", "johnDoeGH", "JohnDoe", "john.doe"))))
        val students: List<Student> = mapper.readValue(mockMvc.perform(get(path)).andReturn().response.contentAsString)

        val user = students.find { it.lastName == "Doe" } !!
        Assertions.assertEquals("johnDoe", user.userName)
        Assertions.assertEquals("John", user.firstName)
        Assertions.assertEquals("Doe", user.lastName)
        Assertions.assertEquals(1/1/1990, user.dateOfBirth)
        Assertions.assertEquals(byteArrayOf(), user.profilePicture)
        Assertions.assertEquals("john.doe@gmail.com", user.email)
        Assertions.assertEquals("bachelor", user.studyType)
        Assertions.assertEquals(2, user.year)
        Assertions.assertEquals("1.2", user.group)
        Assertions.assertEquals("johnDoeGH", user.githubUser)
        Assertions.assertEquals("JohnDoe", user.facebookUser)
        Assertions.assertEquals("john.doe", user.eduUser)
    }
}
