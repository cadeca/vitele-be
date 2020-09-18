package ro.cadeca.weasylearn.questions.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ro.cadeca.weasylearn.questions.model.QuestionType

@Document
class QuestionDoc(
        var subjectId: String,
        var type: QuestionType,
        var description: String,
        var hint: String? = null,
        var feedback: String? = null,
        var tags: List<String>? = null,
        var data: Any? = null,
        @Id
        var id: String? = null
)
