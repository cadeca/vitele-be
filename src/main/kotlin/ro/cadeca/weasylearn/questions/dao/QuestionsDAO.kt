package ro.cadeca.weasylearn.questions.dao

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.questions.model.Question
import ro.cadeca.weasylearn.questions.persistence.QuestionDoc
import ro.cadeca.weasylearn.questions.persistence.QuestionsRepository
import ro.cadeca.weasylearn.services.MapperService
import java.util.stream.Collectors

@Service
class QuestionsDAO(
        private val questionsRepository: QuestionsRepository,
        private val mapperService: MapperService,
        private val mongoTemplate: MongoTemplate
) {
    fun save(question: Question) {
        questionsRepository.save(mapperService.map(question))
    }


    fun search(searchString: String?, type: String?): List<Question> {
        val criteria = listOfNotNull(searchString?.let(this::searchCriteria), type?.let(this::typeCriteria))

        return if (criteria.isEmpty())
            findAll()
        else
            mongoTemplate.find(Query(Criteria().orOperator(*criteria.toTypedArray())), QuestionDoc::class.java)
                    .stream().map { mapperService.map<QuestionDoc, Question>(it) }.collect(Collectors.toList())
    }

    private fun typeCriteria(type: String): Criteria = Criteria.where("type").`is`(type)

    private fun searchCriteria(searchString: String): Criteria {
        val containsPattern = Regex(".*$searchString.*").toPattern()
        return Criteria().orOperator(
                Criteria.where("description").regex(containsPattern),
                Criteria.where("hint").regex(containsPattern),
                Criteria.where("feedback").regex(containsPattern),
                Criteria.where("tags").regex(containsPattern)
        )
    }

    fun findAll(): List<Question> {
        return questionsRepository.findAll().map(mapperService::map)
    }
}
