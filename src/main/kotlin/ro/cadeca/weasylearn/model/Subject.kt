package ro.cadeca.weasylearn.model

class Subject(
        var name: String,
        var code: String,
        var description: String?,
        var semester: Int?,
        var teacher: Teacher?,
        var tutors: List<User>?,
        var students: List<Student>?,
        var schedule: List<SubjectSchedule>?
)
