package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.annotations.Converter

@Converter
class UserDocumentToUserProfileDtoConverter : IConverter<UserDocument, UserProfileDTO> {
    override fun convert(source: UserDocument) =
            UserProfileDTO(username = source.username,
                    firstName = source.firstName,
                    lastName = source.lastName,
                    email = source.email,
                    type = source.type)
}
