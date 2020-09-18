package ro.cadeca.weasylearn

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ro.cadeca.weasylearn.persistence.user.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.services.UserService
import java.util.*

class UsersIT : BaseDataIT() {

    @Autowired
    private lateinit var userService: UserService

    private val TEACHER_USER_NAME = "testTeacher"

    private val TEACHER_FIRST_NAME = "testFirstName"

    private val TEACHER_LAST_NAME = "testLastName"

    private val TEACHER_BIRTH_DATE = Calendar.getInstance().also { it.set(2020, 1, 1) }.time
    private val TEACHER_EMAIL = "testEmail@test.com"
    private val TITLES = "titles"
    private val TEACHER_TITLES = listOf("Prof", "Dr", "Ing")

    private val DEPARTMENT = "department"
    private val TEACHER_DEPT = "CTI"

    private val GITHUB_USER = "githubUser"
    private val TEACHER_GITHUB_USER = "hackerTM"

    private val EDU_USER = "eduUser"
    private val TEACHER_EDU_USER = "test@upt.ro"

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `test retrieve teacher with all details`() {
        val ud = UserDocument(username = TEACHER_USER_NAME,
                firstName = TEACHER_FIRST_NAME,
                lastName = TEACHER_LAST_NAME,
                dateOfBirth = TEACHER_BIRTH_DATE,
                email = TEACHER_EMAIL,
                type = TEACHER,
                details = mapOf(DEPARTMENT to TEACHER_DEPT,
                        TITLES to TEACHER_TITLES,
                        GITHUB_USER to TEACHER_GITHUB_USER,
                        EDU_USER to TEACHER_EDU_USER
                )
        )

        userRepository.save(ud)

        val allTeachers = userService.findAllTeachers()
        assertNotNull(allTeachers)
        assertEquals(1, allTeachers.size)
        val theTeacher = allTeachers.first()
        assertNotNull(theTeacher)
        assertEquals(TEACHER_USER_NAME, theTeacher.userName)
        assertEquals(TEACHER_FIRST_NAME, theTeacher.firstName)
        assertEquals(TEACHER_LAST_NAME, theTeacher.lastName)
        assertEquals(TEACHER_BIRTH_DATE, theTeacher.dateOfBirth)
        assertEquals(TEACHER_EMAIL, theTeacher.email)
        assertEquals(TEACHER_DEPT, theTeacher.department)
        assertEquals(TEACHER_TITLES.toSet(), theTeacher.titles?.toSet())
        assertEquals(TEACHER_EDU_USER, theTeacher.eduUser)
        assertEquals(TEACHER_GITHUB_USER, theTeacher.githubUser)
    }
}
