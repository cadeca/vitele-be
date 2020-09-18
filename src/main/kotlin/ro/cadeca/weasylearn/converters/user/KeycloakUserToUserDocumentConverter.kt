package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.services.UserTypeSelector

@Converter
class KeycloakUserToUserDocumentConverter(private val userTypeSelector: UserTypeSelector) : IConverter<KeycloakUser, UserDocument> {
    override fun convert(a: KeycloakUser) =
            UserDocument(username = a.username,
                    firstName = a.firstName,
                    lastName = a.lastName,
                    email = a.email,
                    type = userTypeSelector.selectType(a))
}
