package ro.cadeca.weasylearn.questions

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.questions.dto.QuestionDTO
import ro.cadeca.weasylearn.questions.model.QuestionType
import ro.cadeca.weasylearn.questions.persistence.QuestionDoc

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddAndSearchQuestionsIT : BaseDataIT() {

    private val path = "/api/questions"

    private val mapper = jacksonObjectMapper()

    @BeforeAll
    @WithMockKeycloakAuth(TEACHER)
    fun prepareDatabase() {
        addQuestion(QuestionDTO("subj", QuestionType.FREE_TEXT, "description", tags = listOf("tag", "good")))
        addQuestion(QuestionDTO("subj1", QuestionType.FREE_TEXT, "free text", tags = listOf("good")))
        addQuestion(QuestionDTO("subj2", QuestionType.SINGLE_CHOICE, "single"))
        addQuestion(QuestionDTO("subj3", QuestionType.SINGLE_CHOICE, "choice", tags = listOf("my tag")))
        addQuestion(QuestionDTO("subj4", QuestionType.SINGLE_CHOICE, "good question"))
    }

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `search questions by search string`() {
        val questions: List<QuestionDoc> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get(path).param("search", "good"))
                .andReturn().response.contentAsString)
        assertEquals(3, questions.size)
        assertTrue(questions.map { it.subjectId }.toSet().containsAll(setOf("subj", "subj1", "subj4")))
    }

    @Test
    @WithMockKeycloakAuth(TEACHER)
    fun `get questions by type`() {
        val questions: List<QuestionDoc> = mapper.readValue(mockMvc().perform(MockMvcRequestBuilders.get(path).param("type", QuestionType.FREE_TEXT.toString()))
                .andReturn().response.contentAsString)
        assertEquals(2, questions.size)
        assertTrue(questions.map { it.subjectId }.toSet().containsAll(setOf("subj", "subj1")))
    }


    private fun addQuestion(questionDTO: QuestionDTO) {
        mockMvc().with(keycloakAuthenticationToken().authorities(TEACHER)).perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(questionDTO)))
    }
}
