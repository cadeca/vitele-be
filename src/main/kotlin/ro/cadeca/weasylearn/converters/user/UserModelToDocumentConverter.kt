package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER

@Converter
class UserModelToDocumentConverter : IConverter<User, UserDocument> {
    override fun convert(source: User) =
            UserDocument(source.username, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email, USER, emptyMap())
}
