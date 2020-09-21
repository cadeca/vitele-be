package ro.cadeca.weasylearn.dto.subjects

import ro.cadeca.weasylearn.dto.StudentsGroupDTO
import ro.cadeca.weasylearn.dto.UserDTO
import ro.cadeca.weasylearn.model.misc.TimeAndLocation

class SubjectScheduleDTO(
        var timeAndLocation: List<TimeAndLocation>,
        var tutor: UserDTO,
        var studentsGroup: StudentsGroupDTO
)
