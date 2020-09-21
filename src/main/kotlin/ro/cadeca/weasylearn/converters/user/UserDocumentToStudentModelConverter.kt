package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT

@Converter
class UserDocumentToStudentModelConverter : IConverter<UserDocument, Student> {
    override fun convert(source: UserDocument): Student {
        if (source.type != STUDENT)
            throw ConversionException("Could not convert document to student: $source")

        val student = Student(source.username, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email)

        source.details.let { details ->
            details["studyType"]?.let { student.studyType = it as String }
            details["year"]?.let { student.year = it as Int }
            details["group"]?.let { student.group = it as String }
            details["githubUser"]?.let { student.githubUser = it as String }
            details["facebookUser"]?.let { student.facebookUser = it as String }
            details["eduUser"]?.let { student.eduUser = it as String }
        }

        return student
    }
}
