package ro.cadeca.weasylearn.users

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles
import ro.cadeca.weasylearn.controllers.UserController
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.user.StudyType.Companion.BACHELOR
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.services.UserService
import ro.cadeca.weasylearn.student2
import java.util.*

class UserServiceIT : BaseDataIT() {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `test retrieve student with all details`() {
        val ud = UserDocument(username = STUDENT_USER_NAME,
                firstName = STUDENT_FIRST_NAME,
                lastName = STUDENT_LAST_NAME,
                dateOfBirth = STUDENT_BIRTH_DATE,
                email = STUDENT_EMAIL,
                type = STUDENT,
                details = mapOf(STUDY_TYPE to STUDENT_STUDY_TYPE,
                        YEAR to STUDENT_YEAR,
                        GROUP to STUDENT_GROUP,
                        GITHUB_USER to STUDENT_GITHUB_USER,
                        FACEBOOK_USER to STUDENT_FACEBOOK_USER,
                        EDU_USER to STUDENT_EDU_USER
                )
        )

        userRepository.save(ud)

        val theStudent = userService.findUserByUsername(ud.username) as Student
        assertNotNull(theStudent)
        assertEquals(STUDENT_USER_NAME, theStudent.username)
        assertEquals(STUDENT_FIRST_NAME, theStudent.firstName)
        assertEquals(STUDENT_LAST_NAME, theStudent.lastName)
        assertEquals(STUDENT_BIRTH_DATE, theStudent.dateOfBirth)
        assertEquals(STUDENT_EMAIL, theStudent.email)
        assertEquals(STUDENT_STUDY_TYPE, theStudent.studyType)
        assertEquals(STUDENT_YEAR, theStudent.year)
        assertEquals(STUDENT_GROUP, theStudent.group)
        assertEquals(STUDENT_GITHUB_USER, theStudent.githubUser)
        assertEquals(STUDENT_FACEBOOK_USER, theStudent.facebookUser)
        assertEquals(STUDENT_EDU_USER, theStudent.eduUser)

        userRepository.delete(ud)
    }

    @Test
    fun `test retrieve teacher with all details`() {
        val ud = UserDocument(username = TEACHER_USER_NAME,
                firstName = TEACHER_FIRST_NAME,
                lastName = TEACHER_LAST_NAME,
                dateOfBirth = TEACHER_BIRTH_DATE,
                email = TEACHER_EMAIL,
                type = UserTypes.TEACHER,
                details = mapOf(DEPARTMENT to TEACHER_DEPT,
                        TITLES to TEACHER_TITLES,
                        EDU_USER to TEACHER_EDU_USER,
                        GITHUB_USER to TEACHER_GITHUB_USER
                )
        )

        userRepository.save(ud)

        val theTeacher = userService.findUserByUsername(ud.username) as Teacher
        assertNotNull(theTeacher)
        assertEquals(TEACHER_USER_NAME, theTeacher.username)
        assertEquals(TEACHER_FIRST_NAME, theTeacher.firstName)
        assertEquals(TEACHER_LAST_NAME, theTeacher.lastName)
        assertEquals(TEACHER_BIRTH_DATE, theTeacher.dateOfBirth)
        assertEquals(TEACHER_EMAIL, theTeacher.email)
        assertEquals(TEACHER_DEPT, theTeacher.department)
        assertEquals(TEACHER_TITLES.toSet(), theTeacher.titles?.toSet())
        assertEquals(TEACHER_EDU_USER, theTeacher.eduUser)
        assertEquals(TEACHER_GITHUB_USER, theTeacher.githubUser)

        userRepository.delete(ud)
    }

    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `can change user type`() {
        val mapper = jacksonObjectMapper()
        val userType = UserController.UserType(student2.username, Roles.ADMIN)
        val result = mockMvc().perform(MockMvcRequestBuilders.put("/api/user/type")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userType", mapper.writeValueAsString(userType)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
        assertTrue(userService.isType(student2.username, Roles.ADMIN))
        assertEquals(200, result.response.status, "")
    }

    @Test
    fun `test retrieve otherUser with all details`() {
        val ud = UserDocument(username = OTHER_USER_USER_NAME,
                firstName = OTHER_USER_FIRST_NAME,
                lastName = OTHER_USER_LAST_NAME,
                dateOfBirth = OTHER_USER_BIRTH_DATE,
                email = OTHER_USER_EMAIL,
                type = UserTypes.USER
        )

        userRepository.save(ud)

        val theOtherUser = userService.findUserByUsername(ud.username)
        assertNotNull(theOtherUser)
        assertEquals(OTHER_USER_USER_NAME, theOtherUser.username)
        assertEquals(OTHER_USER_FIRST_NAME, theOtherUser.firstName)
        assertEquals(OTHER_USER_LAST_NAME, theOtherUser.lastName)
        assertEquals(OTHER_USER_BIRTH_DATE, theOtherUser.dateOfBirth)
        assertEquals(OTHER_USER_EMAIL, theOtherUser.email)

        userRepository.delete(ud)
    }

    companion object {
        // ******* start Student *******
        private val STUDENT_USER_NAME = "testStudent"
        private val STUDENT_FIRST_NAME = "testFirstName"
        private val STUDENT_LAST_NAME = "testLastName"
        private val STUDENT_BIRTH_DATE = Calendar.getInstance().also { it.set(2020, 1, 1) }.time
        private val STUDENT_EMAIL = "testEmail@test.com"

        private val STUDY_TYPE = "studyType"
        private val STUDENT_STUDY_TYPE = BACHELOR

        private val YEAR = "year"
        private val STUDENT_YEAR = 2

        private val GROUP = "group"
        private val STUDENT_GROUP = "2.1"

        private val GITHUB_USER = "githubUser"
        private val STUDENT_GITHUB_USER = "hackerTM"

        private val FACEBOOK_USER = "facebookUser"
        private val STUDENT_FACEBOOK_USER = "hackerTMFacebook"

        private val EDU_USER = "eduUser"
        private val STUDENT_EDU_USER = "test@student.upt.ro"
        // ******* end Student *******

        // ******* start Teacher *******
        private val TEACHER_USER_NAME = "testTeacher"
        private val TEACHER_FIRST_NAME = "testFirstName"
        private val TEACHER_LAST_NAME = "testLastName"
        private val TEACHER_BIRTH_DATE = Calendar.getInstance().also { it.set(2020, 1, 1) }.time
        private val TEACHER_EMAIL = "testEmail@test.com"

        private val TITLES = "titles"
        private val TEACHER_TITLES = listOf("Prof", "Dr", "Ing")

        private val DEPARTMENT = "department"
        private val TEACHER_DEPT = "CTI"

        private val TEACHER_EDU_USER = "test@upt.ro"
        private val TEACHER_GITHUB_USER = "hackerTM"
        // ******* end Teacher *******

        // ******* start Other User *******
        private val OTHER_USER_USER_NAME = "testUser"
        private val OTHER_USER_FIRST_NAME = "testFirstName"
        private val OTHER_USER_LAST_NAME = "testLastName"
        private val OTHER_USER_BIRTH_DATE = Calendar.getInstance().also { it.set(2020, 1, 1) }.time
        private val OTHER_USER_EMAIL = "testEmail@test.com"
        // ******* end Other User *******
    }
}
