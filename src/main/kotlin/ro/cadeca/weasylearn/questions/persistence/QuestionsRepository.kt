package ro.cadeca.weasylearn.questions.persistence

import org.springframework.data.mongodb.repository.MongoRepository

interface QuestionsRepository : MongoRepository<QuestionDoc, String> {

}
