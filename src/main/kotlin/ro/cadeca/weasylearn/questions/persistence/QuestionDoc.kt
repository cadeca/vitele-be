package ro.cadeca.weasylearn.questions.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class QuestionDoc(
        @Id
        var id: String? = null,
        var text: String,
        var number: Number
)
