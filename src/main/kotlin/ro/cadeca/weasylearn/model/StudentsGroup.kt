package ro.cadeca.weasylearn.model

import ro.cadeca.weasylearn.model.misc.TimeAndLocation

class StudentsGroup(
        var name: String,
        var code: String,
        var classTimeAndLocation: List<TimeAndLocation>,
        var students: List<Student>
)
