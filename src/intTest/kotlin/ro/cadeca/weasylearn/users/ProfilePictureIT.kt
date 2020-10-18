package ro.cadeca.weasylearn.users

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.student1


class ProfilePictureIT : BaseDataIT() {
    private val path = "/api/user/profile/image"

    @Test
    fun `test user profile image upload and get`() {
        val mockAuth = mockAuth(student1.username, STUDENT)
        mockMvc().with(mockAuth)
                .perform(multipart(path)
                        .file("file", "content".toByteArray()))
                .andExpect(status().isOk)

        val contentAsString = mockMvc().with(mockAuth)
                .perform(get(path))
                .andReturn().response.contentAsString;
        assertEquals("content", contentAsString)
    }
}
