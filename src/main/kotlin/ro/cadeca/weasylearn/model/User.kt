package ro.cadeca.weasylearn.model

import ro.cadeca.weasylearn.persistence.subject.SubjectEntity

class User(
        var lastName: String? = null,
        var firstName: String? = null,
        var year: Int? = null,
        var groupId: String? = null,
        var subjects: List<SubjectEntity>? = null,
        var university: String? = null,
        var faculty: String? = null,
        var department: String? = null
)
