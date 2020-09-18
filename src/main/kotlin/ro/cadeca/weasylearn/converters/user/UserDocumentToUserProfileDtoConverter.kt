package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.annotations.Converter

@Converter
class UserDocumentToUserProfileDtoConverter : IConverter<UserDocument, UserProfileDTO> {
    override fun convert(a: UserDocument) =
            UserProfileDTO(username = a.username,
                    firstName = a.firstName,
                    lastName = a.lastName,
                    email = a.email,
                    type = a.type)
}
