package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.persistence.user.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserDocument
import javax.persistence.Converter

@Converter
class StudentModelToDocumentConverter : IConverter<Student, UserDocument> {
    override fun convert(a: Student) =
            UserDocument(a.userName, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email, STUDENT, mapStudentDetails(a))

    private fun mapStudentDetails(student: Student): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        student.year?.let { map["year"] = it }
        student.group?.let { map["group"] = it }
        student.githubUser?.let { map["githubUser"] = it }
        student.facebookUser?.let { map["facebookUser"] = it }
        student.eduUser?.let { map["eduUser"] = it }

        return map
    }
}
