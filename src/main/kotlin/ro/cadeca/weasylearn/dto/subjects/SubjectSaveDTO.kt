package ro.cadeca.weasylearn.dto.subjects

class SubjectSaveDTO(
        var name: String,
        var code: String,
        var description: String? = null,
        var semester: Int? = null,
        var teacher: String? = null,
        var tutors: List<String>? = null,
        var students: List<String>? = null,
        var schedule: List<SubjectScheduleDTO>? = null,
        var id: Long? = null
)
