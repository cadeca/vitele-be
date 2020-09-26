package ro.cadeca.weasylearn.subjects

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import ro.cadeca.weasylearn.BaseDataIT
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateSubjectIT : BaseDataIT() {

    private val path = "/api/subject"

    private val mapper = jacksonObjectMapper()

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    private val teacher = UserDocument(
            username = "berlin",
            firstName = "Berlin",
            lastName = "Fonollosa",
            email = "berlin.fonollosa@aut.upt.ro",
            type = UserTypes.TEACHER,
            details = mapOf("department" to "AIA",
                    "titles" to listOf("conf.", "dr.", "ing."),
                    "eduUser" to "berlin.fonollosa",
                    "githubUser" to "berlinFonollosa"
            )
    )
    private val studentDoe = UserDocument(
            username = "johnDoe",
            firstName = "John Albert",
            lastName = "Doe",
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
    private val studentSnow = UserDocument(username = "JohnSnow",
            firstName = "John",
            lastName = "Snow",
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

    @BeforeAll
    fun prepareDatabase() {
        subjectRepository.save(SubjectEntity("subj 1 e2", "code1", "description1"))
        subjectRepository.save(SubjectEntity("subj2", "code2", semester = 1))

        userRepository.save(teacher)
        userRepository.save(studentDoe)
        userRepository.save(studentSnow)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `get subject and update it to change teacher tutors and students`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", " 1")).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
        val subject = SubjectSaveDTO(
                name = subjects.first().name,
                code = subjects.first().name,
                id = subjects.first().id,
                teacher = teacher.username,
                students = listOf(studentDoe.username, studentSnow.username),
                tutors = listOf(studentDoe.username, teacher.username)
        )

        mockMvc().perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(subject)))

        val newSubjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", " 1")).andReturn().response.contentAsString)
        val newSubject = newSubjects.first()

        assertEquals("code1", newSubject.code)
        assertEquals(teacher.email, newSubject.teacher?.email)
        assertEquals(2, newSubject.students?.size)
        assertTrue(newSubject.students?.map { it.email }?.containsAll(setOf(studentDoe.email, studentSnow.email))
                ?: fail("Students is null"))
        assertEquals(2, newSubject.tutors?.size)
        assertTrue(newSubject.tutors?.map { it.email }?.containsAll(setOf(studentDoe.email, teacher.email))
                ?: fail("Tutors is null"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `get subject and update it to change description and add semester`() {
        val subjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", " 1")).andReturn().response.contentAsString)
        assertEquals(1, subjects.size)
        val subject = subjects.first().apply {
            description = "new description"
            semester = 2
        }

        mockMvc().perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(subject)))

        val newSubjects: List<SubjectDTO> = mapper.readValue(mockMvc().perform(get(path).param("query", " 1")).andReturn().response.contentAsString)
        val newSubject = newSubjects.first()

        assertEquals("code1", newSubject.code)
        assertEquals("new description", newSubject.description)
        assertEquals(2, newSubject.semester)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add teacher to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/teacher").param("username", "berlin"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertEquals("berlin.fonollosa@aut.upt.ro", subject.teacher?.email)
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add tutors to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/tutor").param("username", "berlin"))
        mockMvc().perform(put("$path/$subjectId/tutor").param("username", "johnDoe"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertTrue(subject.tutors?.map { it.email }?.containsAll(listOf("berlin.fonollosa@aut.upt.ro", "john.doe@student.upt.ro"))
                ?: fail("tutors field is nul"))
    }

    @Test
    @WithMockKeycloakAuth(ADMIN)
    fun `add students to subject`() {
        val subjectId = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first().id
        mockMvc().perform(put("$path/$subjectId/student").param("username", "JohnSnow"))
        mockMvc().perform(put("$path/$subjectId/student").param("username", "johnDoe"))

        val subject = mapper.readValue<List<SubjectDTO>>(mockMvc().perform(get(path).param("query", " 1"))
                .andReturn().response.contentAsString).first()
        assertTrue(subject.students?.map { it.email }?.containsAll(listOf("john.snow@student.upt.ro", "john.doe@student.upt.ro"))
                ?: fail("students field is nul"))
    }
}
