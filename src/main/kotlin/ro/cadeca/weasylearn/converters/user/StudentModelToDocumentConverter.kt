package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT

@Converter
class StudentModelToDocumentConverter : IConverter<Student, UserDocument> {
    override fun convert(source: Student) =
            UserDocument(source.userName, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email, STUDENT, mapStudentDetails(source))

    private fun mapStudentDetails(student: Student): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        student.studyType?.let { map["studyType"] = it }
        student.year?.let { map["year"] = it }
        student.group?.let { map["group"] = it }
        student.githubUser?.let { map["githubUser"] = it }
        student.facebookUser?.let { map["facebookUser"] = it }
        student.eduUser?.let { map["eduUser"] = it }

        return map
    }
}
