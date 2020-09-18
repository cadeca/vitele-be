package ro.cadeca.weasylearn.converters.factory

import org.springframework.stereotype.Component
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.user.UserDocumentToStudentModelConverter
import ro.cadeca.weasylearn.converters.user.UserDocumentToTeacherModelConverter
import ro.cadeca.weasylearn.converters.user.UserDocumentToUserModelConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.STUDENT
import ro.cadeca.weasylearn.persistence.user.TEACHER
import ro.cadeca.weasylearn.persistence.user.USER
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Component
class UserDocumentToModelConverterFactory(val userDocumentToUserModelConverter: UserDocumentToUserModelConverter,
                                          val userDocumentToTeacherModelConverter: UserDocumentToTeacherModelConverter,
                                          val userDocumentToStudentModelConverter: UserDocumentToStudentModelConverter) {

    fun getDocumentToModelConverter(type: String): IConverter<UserDocument, out User> {
        if (type == USER)
            return userDocumentToUserModelConverter

        if (type == TEACHER)
            return userDocumentToTeacherModelConverter

        if (type == STUDENT)
            return userDocumentToStudentModelConverter

        throw IllegalArgumentException("Type $type is not a valid userType")
    }
}
