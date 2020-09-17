package ro.cadeca.weasylearn.questions.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.STUDENT
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.questions.dto.QuestionDTO
import ro.cadeca.weasylearn.questions.model.Question
import ro.cadeca.weasylearn.questions.model.QuestionType
import ro.cadeca.weasylearn.questions.model.questions.ChoiceQuestion
import ro.cadeca.weasylearn.questions.persistence.QuestionDoc
import ro.cadeca.weasylearn.questions.persistence.repositories.QuestionRepository
import ro.cadeca.weasylearn.questions.services.QuestionService
import ro.cadeca.weasylearn.services.MapperService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/questions")
class QuestionsController(
        private val questionService: QuestionService,
        private val mapperService: MapperService,
        private val questionRepository: QuestionRepository
) {

    init {
        mapperService.configureClassMap<Question, QuestionDTO> {
            it.field("subject.id", "subjectId")
            it.mapNulls(true)
        }
        mapperService.configureClassMap<QuestionDTO, Question> { }
    }

    @PutMapping
    @RolesAllowed(TEACHER)
    fun createChoiceQuestion(@RequestBody questionDTO: QuestionDTO) {
        if (ChoiceQuestion.allowedTypes.contains(questionDTO.type))
            questionService.addChoiceQuestion(mapperService.map(questionDTO))
        else
            questionService.addSimpleQuestion(mapperService.map(questionDTO))
    }

    @GetMapping
    @RolesAllowed(TEACHER, STUDENT)
    fun getQuestions(@RequestParam type: QuestionType?, @RequestParam tag: String?): List<QuestionDTO> =
            questionService.filter(type, tag).map(mapperService::map)


    @GetMapping("add")
    fun addMongoQuestion() {
        questionRepository.save(QuestionDoc(text = "text", number = 3))
    }

    @GetMapping("getMongo")
    fun getMongoQuestions() = questionRepository.findAll()
}
