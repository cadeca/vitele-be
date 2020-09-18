package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.USER
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Converter
class UserDocumentToUserModelConverter : IConverter<UserDocument, User> {
    override fun convert(a: UserDocument): User {
        if (a.type != USER)
            throw ConversionException("Could not convert document to user: $a")

        return User(a.username, a.firstName, a.lastName, a.dateOfBirth, a.profilePicture, a.email)
    }
}
