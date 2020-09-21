package ro.cadeca.weasylearn.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER
import ro.cadeca.weasylearn.services.UserService
import java.util.*

class OtherUserIT : BaseDataIT() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    private val OTHER_USER_USER_NAME = "testUser"
    private val OTHER_USER_FIRST_NAME = "testFirstName"
    private val OTHER_USER_LAST_NAME = "testLastName"
    private val OTHER_USER_BIRTH_DATE = Calendar.getInstance().also { it.set(2020, 1, 1) }.time
    private val OTHER_USER_EMAIL = "testEmail@test.com"

    @Test
    fun `test retrieve otherUser with all details`() {
        val ud = UserDocument(username = OTHER_USER_USER_NAME,
                firstName = OTHER_USER_FIRST_NAME,
                lastName = OTHER_USER_LAST_NAME,
                dateOfBirth = OTHER_USER_BIRTH_DATE,
                email = OTHER_USER_EMAIL,
                type = USER
        )

        userRepository.save(ud)

        val allOtherUsers = userService.findAllOtherUsers()
        Assertions.assertNotNull(allOtherUsers)
        Assertions.assertEquals(1, allOtherUsers.size)
        val theOtherUser = allOtherUsers.first()
        Assertions.assertNotNull(theOtherUser)
        Assertions.assertEquals(OTHER_USER_USER_NAME, theOtherUser.username)
        Assertions.assertEquals(OTHER_USER_FIRST_NAME, theOtherUser.firstName)
        Assertions.assertEquals(OTHER_USER_LAST_NAME, theOtherUser.lastName)
        Assertions.assertEquals(OTHER_USER_BIRTH_DATE, theOtherUser.dateOfBirth)
        Assertions.assertEquals(OTHER_USER_EMAIL, theOtherUser.email)
    }
}
