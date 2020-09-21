package ro.cadeca.weasylearn.questions.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.questions.dao.QuestionsDAO
import ro.cadeca.weasylearn.questions.dto.QuestionDTO
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/questions")
class QuestionsController(
        private val questionsDAO: QuestionsDAO,
        private val mapperService: MapperService
) {
    @PutMapping
    @RolesAllowed(TEACHER)
    fun addQuestion(@RequestBody questionDTO: QuestionDTO) {
        questionsDAO.save(mapperService.map(questionDTO))
    }

    @GetMapping
    @RolesAllowed(TEACHER)
    fun getQuestions(
            @RequestParam(required = false) search: String?,
            @RequestParam(required = false) type: String?
    ): List<QuestionDTO> {
        return questionsDAO.search(search, type).map(mapperService::map)
    }
}
