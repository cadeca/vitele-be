package ro.cadeca.weasylearn

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ro.cadeca.weasylearn.dto.StudentDTO
import ro.cadeca.weasylearn.dto.TeacherDTO
import ro.cadeca.weasylearn.dto.UserDTO
import ro.cadeca.weasylearn.dto.UserWrapperDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchUsersIT : BaseDataIT() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/user"

    private val mapper = jacksonObjectMapper()

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeAll
    fun setup() {
        val user1 = UserDocument(username = "rioCity",
                firstName = "Rio",
                lastName = "City",
                dateOfBirth = Calendar.getInstance().also { it.set(1985, 1, 1) }.time,
                email = "rio@gmail.com",
                type = USER
        )

        val user2 = UserDocument(username = "tokyo",
                firstName = "Tokyo Drift",
                lastName = "City",
                dateOfBirth = Calendar.getInstance().also { it.set(1990, 2, 2) }.time,
                email = "tokyo@yahoo.com",
                type = USER
        )

        val teacher1 = UserDocument(username = "elProfessor",
                firstName = "Professor",
                lastName = "Papel",
                dateOfBirth = Calendar.getInstance().also { it.set(1987, 4, 20) }.time,
                email = "professor.papel@upt.ro",
                type = TEACHER,
                details = mapOf("department" to "CTI",
                        "titles" to listOf("prof.", "dr.", "ing."),
                        "eduUser" to "professor.papel",
                        "githubUser" to "professorPapel"
                )
        )

        val teacher2 = UserDocument(username = "berlin",
                firstName = "Berlin",
                lastName = "Fonollosa",
                dateOfBirth = Calendar.getInstance().also { it.set(1982, 10, 9) }.time,
                email = "berlin.fonollosa@aut.upt.ro",
                type = TEACHER,
                details = mapOf("department" to "AIA",
                        "titles" to listOf("conf.", "dr.", "ing."),
                        "eduUser" to "berlin.fonollosa",
                        "githubUser" to "berlinFonollosa"
                )
        )

        val student1 = UserDocument(username = "johnDoe",
                firstName = "John Albert",
                lastName = "Doe",
                dateOfBirth = Calendar.getInstance().also { it.set(2000, 6, 10) }.time,
                email = "john.doe@student.upt.ro",
                type = STUDENT,
                details = mapOf("studyType" to "Bachelor",
                        "year" to 2,
                        "group" to "2.1",
                        "githubUser" to "john_doe",
                        "facebookUser" to "JohnDoe",
                        "eduUser" to "john.doe"
                )
        )

        val student2 = UserDocument(username = "JohnSnow",
                firstName = "John",
                lastName = "Snow",
                dateOfBirth = Calendar.getInstance().also { it.set(1997, 5, 10) }.time,
                email = "john.snow@student.upt.ro",
                type = STUDENT,
                details = mapOf("studyType" to "Master",
                        "year" to 1,
                        "group" to "2.2",
                        "githubUser" to "john_snow",
                        "facebookUser" to "JohnSnow",
                        "eduUser" to "john.snow"
                )
        )

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(teacher1)
        userRepository.save(teacher2)
        userRepository.save(student1)
        userRepository.save(student2)
    }


    @Test
    fun `get all users`() {
        val allUsers: List<UserWrapperDTO> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/all")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allUsers)
        Assertions.assertEquals(6, allUsers.size)

    }

    @Test
    fun `get all teachers`() {
        val allTeachers: List<TeacherDTO> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/teachers")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allTeachers)
        Assertions.assertEquals(2, allTeachers.size)

    }

    @Test
    fun `get all students`() {
        val allStudents: List<StudentDTO> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/students")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allStudents)
        Assertions.assertEquals(2, allStudents.size)
    }

    @Test
    fun `get all otherUsers`() {
        val allOtherUsers: List<UserDTO> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/others")).andReturn().response.contentAsString)
        Assertions.assertNotNull(allOtherUsers)
        Assertions.assertEquals(2, allOtherUsers.size)
    }

    @Test
    fun `get user by username`() {
        val user: UserDTO = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/rioCity")).andReturn().response.contentAsString)

        Assertions.assertNotNull(user)
        Assertions.assertEquals("rioCity", user.userName)
        Assertions.assertEquals("Rio", user.firstName)
        Assertions.assertEquals("City", user.lastName)
        Assertions.assertEquals(Calendar.getInstance().also { it.set(1985, 1, 1) }.time, user.dateOfBirth)
        Assertions.assertEquals("rio@gmail.com", user.email)
    }

    @Test
    fun `get teacher by username`() {
        val user: TeacherDTO = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/elProfessor")).andReturn().response.contentAsString)

        Assertions.assertNotNull(user)
        Assertions.assertEquals("elProfessor", user.userName)
        Assertions.assertEquals("Professor", user.firstName)
        Assertions.assertEquals("Papel", user.lastName)
        Assertions.assertEquals(Calendar.getInstance().also { it.set(1987, 4, 20) }.time, user.dateOfBirth)
        Assertions.assertEquals("professor.papel@upt.ro", user.email)
        Assertions.assertEquals("CTI", user.department)
        Assertions.assertEquals(listOf("prof.", "dr.", "ing."), user.titles)
        Assertions.assertEquals("professor.papel", user.eduUser)
        Assertions.assertEquals("professorPapel", user.githubUser)
    }

    @Test
    fun `get student by username`() {
        val user: StudentDTO = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/JohnSnow")).andReturn().response.contentAsString)

        Assertions.assertNotNull(user)
        Assertions.assertEquals("JohnSnow", user.userName)
        Assertions.assertEquals("John", user.firstName)
        Assertions.assertEquals("Snow", user.lastName)
        Assertions.assertEquals(Calendar.getInstance().also { it.set(1997, 5, 10) }.time, user.dateOfBirth)
        Assertions.assertEquals("john.snow@student.upt.ro", user.email)
        Assertions.assertEquals("Master", user.studyType)
        Assertions.assertEquals(1, user.year)
        Assertions.assertEquals("2.2", user.group)
        Assertions.assertEquals("john_snow", user.githubUser)
        Assertions.assertEquals("JohnSnow", user.facebookUser)
        Assertions.assertEquals("john.snow", user.eduUser)
    }

    @Test
    fun `search by username`() {
        val userList: List<User> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/search&JohnSnow")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        Assertions.assertEquals(1, userList.size)
        val user = userList.first() as Student

        Assertions.assertNotNull(user)
        Assertions.assertEquals("JohnSnow", user.userName)
        Assertions.assertEquals("John", user.firstName)
        Assertions.assertEquals("Snow", user.lastName)
        Assertions.assertEquals(Calendar.getInstance().also { it.set(1997, 5, 10) }.time, user.dateOfBirth)
        Assertions.assertEquals("john.snow@student.upt.ro", user.email)
        Assertions.assertEquals("Master", user.studyType)
        Assertions.assertEquals(1, user.year)
        Assertions.assertEquals("2.2", user.group)
        Assertions.assertEquals("john_snow", user.githubUser)
        Assertions.assertEquals("JohnSnow", user.facebookUser)
        Assertions.assertEquals("john.snow", user.eduUser)
    }

    @Test
    fun `search by firstName`() {
        val userList: List<User> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/search&John")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        Assertions.assertEquals(2, userList.size)

        val user1 = userList.first()
        val user2 = userList[1]

        Assertions.assertEquals("John Albert", user1.firstName)
        Assertions.assertEquals("Doe", user1.lastName)

        Assertions.assertEquals("John", user2.firstName)
        Assertions.assertEquals("Snow", user2.lastName)
    }

    @Test
    fun `search by fullName`() {
        val userList: List<User> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/search&John Albert Doe")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        Assertions.assertEquals(1, userList.size)

        val user = userList.first()

        Assertions.assertEquals("John Albert", user.firstName)
        Assertions.assertEquals("Doe", user.lastName)
    }

    @Test
    fun `search by two firstNames`() {
        val userList: List<User> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/search&John Albert")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        Assertions.assertEquals(1, userList.size)

        val user = userList.first()

        Assertions.assertEquals("John Albert", user.firstName)
        Assertions.assertEquals("Doe", user.lastName)
    }

    @Test
    fun `search by email`() {
        val userList: List<User> = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("$path/search&professor.papel@upt.ro")).andReturn().response.contentAsString)
        Assertions.assertNotNull(userList)
        Assertions.assertEquals(1, userList.size)

        val user = userList.first()

        Assertions.assertEquals("Professor", user.firstName)
        Assertions.assertEquals("Papel", user.lastName)
    }
}
