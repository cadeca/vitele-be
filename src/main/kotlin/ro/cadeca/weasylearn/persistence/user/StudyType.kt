package ro.cadeca.weasylearn.persistence.user

import ro.cadeca.weasylearn.model.Student

class StudyType {
    companion object {
        const val BACHELOR = "Bachelor"
        const val MASTER = "Master"
        const val PHD = "PhD"

        val studyTypeByStudentTitle: Map<String, String> = mapOf(
                BACHELOR to "Student",
                MASTER to "Masterand",
                PHD to "Doctorand"
        )

        fun getStudentTitle(student: Student): String {
            return studyTypeByStudentTitle[student.studyType] ?: ""
        }
    }
}
