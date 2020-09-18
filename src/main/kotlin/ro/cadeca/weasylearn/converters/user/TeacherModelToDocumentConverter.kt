package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.user.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Converter
class TeacherModelToDocumentConverter : IConverter<Teacher, UserDocument> {
    override fun convert(a: Teacher) =
            UserDocument(a.userName, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email, TEACHER, mapTeacherDetails(a))

    private fun mapTeacherDetails(teacher: Teacher): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        teacher.department?.let { map["department"] = it }
        teacher.titles?.let { map["titles"] = it }
        teacher.eduUser?.let { map["eduUser"] = it }
        teacher.githubUser?.let { map["githubUser"] = it }

        return map
    }
}
