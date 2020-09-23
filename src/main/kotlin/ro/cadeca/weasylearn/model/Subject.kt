package ro.cadeca.weasylearn.model

class Subject(
        var name: String,
        var code: String,
        var description: String? = null,
        var semester: Int? = null,
        var teacher: Teacher? = null,
        var tutors: List<User>? = null,
        var students: List<Student>? = null,
        var schedule: List<SubjectSchedule>? = null,
        var id: Long? = null
)
