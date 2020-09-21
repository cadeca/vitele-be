package ro.cadeca.weasylearn.questions.model

class Question(
        var subjectId: String,
        var type: QuestionType,
        var description: String,
        var hint: String? = null,
        var feedback: String? = null,
        var tags: List<String>? = null,
        var data: Any? = null,
        var id: String? = null
)
