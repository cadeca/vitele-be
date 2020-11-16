package ro.cadeca.weasylearn.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER

class UptUserTypeSelectorTest {
    private lateinit var uptUserTypeSelector: UptUserTypeSelector

    @BeforeEach
    internal fun setUp() {
        uptUserTypeSelector = UptUserTypeSelector()
    }

    @Test
    fun `test user with roles student and teacher`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(STUDENT, TEACHER))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(STUDENT))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles teacher`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(TEACHER))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty`() {
        val kcUser = KeycloakUser(username = "testUser", roles = emptyList())

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles something else`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(""))

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student and teacher, username empty`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(STUDENT, TEACHER))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student, username empty`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(STUDENT))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles teacher, username empty`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(TEACHER))

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty, username emtpy`() {
        val kcUser = KeycloakUser(username = "", roles = emptyList())

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles something else, username empty`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(""))

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles and username as an email containing student`() {
        val kcUser = KeycloakUser(username = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles and username as an email not containing student`() {
        val kcUser = KeycloakUser(username = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles and username not as an email`() {
        val kcUser = KeycloakUser(username = "testUser")

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles and username empty`() {
        val kcUser = KeycloakUser(username = "")

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username not an email and email containing student`() {
        val kcUser = KeycloakUser(username = "testUser", email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username not an email and email not containing student`() {
        val kcUser = KeycloakUser(username = "testUser", email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username not an email and email empty`() {
        val kcUser = KeycloakUser(username = "testUser", email = "testUser@gmail.com")

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username not an email and email null`() {
        val kcUser = KeycloakUser(username = "testUser", email = null)

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username empty and email containing student`() {
        val kcUser = KeycloakUser(username = "", email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username empty and email not containing student`() {
        val kcUser = KeycloakUser(username = "", email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username empty and email empty`() {
        val kcUser = KeycloakUser(username = "", email = "")

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with no roles, username empty and email null`() {
        val kcUser = KeycloakUser(username = "", email = null)

        assertEquals(USER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student and teacher, username and email`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(STUDENT, TEACHER), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student, username and email`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(STUDENT), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles teacher, username and email`() {
        val kcUser = KeycloakUser(username = "testUser", roles = listOf(TEACHER), email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student and teacher, username empty and email`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(STUDENT, TEACHER), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles student, username empty and email`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(STUDENT), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles teacher, username empty and email`() {
        val kcUser = KeycloakUser(username = "", roles = listOf(TEACHER), email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty, username and email student`() {
        val kcUser = KeycloakUser(username = "testUser", roles = emptyList(), email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty, username and email teacher`() {
        val kcUser = KeycloakUser(username = "testUser", roles = emptyList(), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty, username student and email teacher`() {
        val kcUser = KeycloakUser(username = "testUser@student.upt.ro", roles = emptyList(), email = "testUser@cs.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT, uptUserTypeSelector.selectType(kcUser))
    }

    @Test
    fun `test user with roles empty, username teacher and email student`() {
        val kcUser = KeycloakUser(username = "testUser@cs.upt.ro", roles = emptyList(), email = "testUser@student.upt.ro")

        assertEquals(ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER, uptUserTypeSelector.selectType(kcUser))
    }
}
