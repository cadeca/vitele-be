package ro.cadeca.weasylearn.users

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles
import ro.cadeca.weasylearn.dto.*
import ro.cadeca.weasylearn.elProfessorBirthDate
import ro.cadeca.weasylearn.rioBirthDate
import ro.cadeca.weasylearn.snowBirthDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class SearchUsersIT : BaseDataIT() {

    private val path = "/api/user"

    private val mapper = jacksonObjectMapper()


    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `get all users`() {
        val allUsers: List<UserWrapperDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/all")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allUsers)
        assertEquals(6, allUsers.size)
    }

    @Test
    @WithMockKeycloakAuth(Roles.TEACHER, Roles.STUDENT)
    fun `not allowed roles`() {
        var result: MvcResult = mockMvc().perform(MockMvcRequestBuilders.get("$path/all")).andReturn()
        assertEquals(403, result.response.status, "Cannot get all users as teacher or as student")
        result = mockMvc().perform(MockMvcRequestBuilders.get("$path/teachers")).andReturn()
        assertEquals(403, result.response.status, "Cannot get all teachers as teacher or as student")
        result = mockMvc().perform(MockMvcRequestBuilders.get("$path/students")).andReturn()
        assertEquals(403, result.response.status, "Cannot get all students as teacher or as student")
        result = mockMvc().perform(MockMvcRequestBuilders.get("$path/others")).andReturn()
        assertEquals(403, result.response.status, "Cannot get all other users as teacher or as student")
    }

    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `get all teachers`() {
        val allTeachers: List<TeacherDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/teachers")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allTeachers)
        assertEquals(2, allTeachers.size)
    }

    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `get all students`() {
        val allStudents: List<StudentDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/students")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allStudents)
        assertEquals(2, allStudents.size)
    }

    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `get all otherUsers`() {
        val allOtherUsers: List<UserDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/others")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allOtherUsers)
        assertEquals(2, allOtherUsers.size)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `get user by username`() {
        val wrapper: UserWrapperDTO = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/rioCity")).andReturn().response.contentAsString)

        val user = wrapper.user!!
        assertEquals("rioCity", user.username)
        assertEquals("Rio", user.firstName)
        assertEquals("City", user.lastName)
        assertEquals(rioBirthDate, user.dateOfBirth)
        assertEquals("rio@gmail.com", user.email)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `get teacher by username`() {
        val wrapper: UserWrapperDTO = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/elProfessor")).andReturn().response.contentAsString)

        val user = wrapper.user!! as TeacherDTO
        assertEquals("elProfessor", user.username)
        assertEquals("Professor", user.firstName)
        assertEquals("Papel", user.lastName)
        assertEquals(elProfessorBirthDate, user.dateOfBirth)
        assertEquals("professor.papel@upt.ro", user.email)
        assertEquals("CTI", user.department)
        assertEquals(listOf("prof.", "dr.", "ing."), user.titles)
        assertEquals("professor.papel", user.eduUser)
        assertEquals("professorPapel", user.githubUser)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `get student by username`() {
        val wrapper: UserWrapperDTO = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/JohnSnow")).andReturn().response.contentAsString)

        val user = wrapper.user!! as StudentDTO
        assertEquals("JohnSnow", user.username)
        assertEquals("John", user.firstName)
        assertEquals("Snow", user.lastName)
        assertEquals(snowBirthDate, user.dateOfBirth)
        assertEquals("john.snow@student.upt.ro", user.email)
        assertEquals("Master", user.studyType)
        assertEquals(1, user.year)
        assertEquals("2.2", user.group)
        assertEquals("john_snow", user.githubUser)
        assertEquals("JohnSnow", user.facebookUser)
        assertEquals("john.snow", user.eduUser)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `search by username`() {
        val userList: List<UserSearchDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/search?query=johnsnow")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        assertEquals(1, userList.size)
        val user = userList.first()

        Assertions.assertNotNull(user)
        assertEquals("JohnSnow", user.username)
        assertEquals("John", user.firstName)
        assertEquals("Snow", user.lastName)
        assertEquals("john.snow@student.upt.ro", user.email)
        assertEquals("Masterand", user.title)

    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `search by firstName`() {
        val userList: List<UserSearchDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/search").param("query", "John")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        assertEquals(2, userList.size)

        val user1 = userList.first()
        val user2 = userList[1]

        assertEquals("John Albert", user1.firstName)
        assertEquals("Doe", user1.lastName)
        assertEquals("Student", user1.title)

        assertEquals("John", user2.firstName)
        assertEquals("Snow", user2.lastName)
        assertEquals("Masterand", user2.title)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `search by fullName`() {
        val userList: List<UserSearchDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/search?query=John Albert Doe")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        assertEquals(1, userList.size)

        val user = userList.first()

        assertEquals("John Albert", user.firstName)
        assertEquals("Doe", user.lastName)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `search by two firstNames`() {
        val usersList: List<UserSearchDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/search?query=John Albert")).andReturn().response.contentAsString)
        Assertions.assertNotNull(usersList)
        assertEquals(1, usersList.size)

        val user = usersList.first()

        assertEquals("John Albert", user.firstName)
        assertEquals("Doe", user.lastName)
    }

    @Test
    @WithMockKeycloakAuth(Roles.STUDENT)
    fun `search by email`() {
        val usersList: List<UserSearchDTO> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get("$path/search?query=professor.papel@upt.ro")).andReturn().response.contentAsString)
        Assertions.assertNotNull(usersList)
        assertEquals(1, usersList.size)

        val user = usersList.first()

        assertEquals("Professor", user.firstName)
        assertEquals("Papel", user.lastName)
    }
}
