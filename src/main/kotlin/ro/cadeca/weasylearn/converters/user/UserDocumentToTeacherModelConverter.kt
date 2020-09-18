package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.user.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserDocument


@Converter
class UserDocumentToTeacherModelConverter : IConverter<UserDocument, Teacher> {
    override fun convert(a: UserDocument): Teacher {
        if (a.type != TEACHER)
            throw ConversionException("Could not convert document to teacher: $a")

        val teacher = Teacher(a.username, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email)

        a.details.let { details ->
            details["department"]?.let { teacher.department = it as String }
            details["titles"]?.let { teacher.titles = it as List<String> }
            details["eduUser"]?.let { teacher.eduUser = it as String }
            details["githubUser"]?.let { teacher.githubUser = it as String }
        }

        return teacher
    }
}
