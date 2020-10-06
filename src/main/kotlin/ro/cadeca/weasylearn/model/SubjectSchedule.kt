package ro.cadeca.weasylearn.model

import ro.cadeca.weasylearn.model.misc.TimeAndLocation

class SubjectSchedule(
        var timeAndLocation: List<TimeAndLocation>,
        var tutor: User,
        var studentsGroup: StudentsGroup
)
