package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER

@Converter
class TeacherModelToDocumentConverter : IConverter<Teacher, UserDocument> {
    override fun convert(source: Teacher) =
            UserDocument(source.userName, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email, TEACHER, mapTeacherDetails(source))

    private fun mapTeacherDetails(teacher: Teacher): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        teacher.department?.let { map["department"] = it }
        teacher.titles?.let { map["titles"] = it }
        teacher.eduUser?.let { map["eduUser"] = it }
        teacher.githubUser?.let { map["githubUser"] = it }

        return map
    }
}
