package ro.cadeca.weasylearn.questions

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import ro.cadeca.weasylearn.DatabaseContainerIT
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.questions.dto.QuestionDTO
import ro.cadeca.weasylearn.questions.model.QuestionType
import ro.cadeca.weasylearn.questions.model.questions.ChoiceQuestion
import ro.cadeca.weasylearn.questions.model.questions.SimpleResponseQuestion
import ro.cadeca.weasylearn.questions.persistence.QuestionDoc

class AddAndGetQuestionIT : DatabaseContainerIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val path = "/api/questions"

    private val mapper = jacksonObjectMapper()

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `i can add a simple question to the database as a teacher`() {
        val description = "simple question"
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(SimpleResponseQuestion(
                        description = description,
                        type = QuestionType.FREE_TEXT
                ))))

        val questions: List<QuestionDTO> = mapper.readValue(mockMvc.perform(get(path)).andReturn().response.contentAsString)
        assertTrue(questions.any { it.description == description })
    }

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `as a teacher i can add a choice question to the database`() {
        val description = "choice question"
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ChoiceQuestion(
                        description = description,
                        type = QuestionType.SINGLE_CHOICE,
                        tags = listOf("tag1")
                ))))

        val questions: List<QuestionDTO> = mapper.readValue(mockMvc.perform(get(path).param("tag", "tag1").param("type", QuestionType.SINGLE_CHOICE.toString())).andReturn().response.contentAsString)
        assertTrue(questions.any { it.description == description })
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `mongo test`() {
        mockMvc.perform(get("$path/add"))
        val questions: List<QuestionDoc>? = mapper.readValue(mockMvc.perform(get("$path/getMongo")).andReturn().response.contentAsString)
        assertEquals(3, questions?.first()?.number)
    }
}
