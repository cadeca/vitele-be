package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.USER
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Converter
class UserModelToDocumentConverter : IConverter<User, UserDocument> {
    override fun convert(a: User) =
            UserDocument(a.userName, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email, USER, emptyMap())
}
