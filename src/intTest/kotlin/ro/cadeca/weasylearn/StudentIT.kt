package ro.cadeca.weasylearn

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ro.cadeca.weasylearn.persistence.user.StudyType.Companion.BACHELOR
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.services.UserService
import java.util.*

class StudentIT : BaseDataIT() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

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

        val allStudents = userService.findAllStudents()
        Assertions.assertNotNull(allStudents)
        Assertions.assertEquals(1, allStudents.size)
        val theStudent = allStudents.first()
        Assertions.assertNotNull(theStudent)
        Assertions.assertEquals(STUDENT_USER_NAME, theStudent.userName)
        Assertions.assertEquals(STUDENT_FIRST_NAME, theStudent.firstName)
        Assertions.assertEquals(STUDENT_LAST_NAME, theStudent.lastName)
        Assertions.assertEquals(STUDENT_BIRTH_DATE, theStudent.dateOfBirth)
        Assertions.assertEquals(STUDENT_EMAIL, theStudent.email)
        Assertions.assertEquals(STUDENT_STUDY_TYPE, theStudent.studyType)
        Assertions.assertEquals(STUDENT_YEAR, theStudent.year)
        Assertions.assertEquals(STUDENT_GROUP, theStudent.group)
        Assertions.assertEquals(STUDENT_GITHUB_USER, theStudent.githubUser)
        Assertions.assertEquals(STUDENT_FACEBOOK_USER, theStudent.facebookUser)
        Assertions.assertEquals(STUDENT_EDU_USER, theStudent.eduUser)
    }
}
