package ro.cadeca.weasylearn.users

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.aspectj.weaver.tools.cache.SimpleCacheFactory.path
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles
import ro.cadeca.weasylearn.controllers.UserController
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.student2
import javax.management.relation.RoleStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserTypeIT : BaseDataIT() {

    private val mapper = jacksonObjectMapper()
    private val path = "/api/user"

    @Test
    @WithMockKeycloakAuth(Roles.ADMIN)
    fun `change user type`() {
        var userType = UserController.UserType(student2.username, Roles.ADMIN)
        var result = mockMvc().perform(MockMvcRequestBuilders.put("$path/type")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userType", mapper.writeValueAsString(userType))).andReturn()
        Assertions.assertEquals(200, result.response.status, "")
    }
}