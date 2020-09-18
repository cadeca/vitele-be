package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.persistence.user.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Converter
class UserDocumentToStudentModelConverter : IConverter<UserDocument, Student> {
    override fun convert(a: UserDocument): Student {
        if (a.type != STUDENT)
            throw ConversionException("Could not convert document to student: $a")

        val student = Student(a.username, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email)

        a.details.let { details ->
            details["year"]?.let { student.year = it as Int }
            details["group"]?.let { student.group = it as String }
            details["githubUser"]?.let { student.githubUser = it as String }
            details["facebookUser"]?.let { student.facebookUser = it as String }
            details["eduUser"]?.let { student.eduUser = it as String }
        }

        return student
    }
}
