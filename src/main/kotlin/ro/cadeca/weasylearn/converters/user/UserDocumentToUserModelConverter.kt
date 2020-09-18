package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.USER
import ro.cadeca.weasylearn.persistence.user.UserDocument

@Converter
class UserDocumentToUserModelConverter : IConverter<UserDocument, User> {
    override fun convert(source: UserDocument): User {
        if (source.type != USER)
            throw ConversionException("Could not convert document to user: $source")

        return User(source.username, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email)
    }
}
