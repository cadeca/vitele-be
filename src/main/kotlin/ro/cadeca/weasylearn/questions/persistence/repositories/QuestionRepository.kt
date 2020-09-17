package ro.cadeca.weasylearn.questions.persistence.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import ro.cadeca.weasylearn.questions.persistence.QuestionDoc

interface QuestionRepository : MongoRepository<QuestionDoc, Long>
