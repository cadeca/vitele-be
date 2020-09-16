package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserEntity
import ro.cadeca.weasylearn.annotations.Converter

@Converter
class UserEntityToModelConverter : IConverter<UserEntity, User> {
    override fun convert(a: UserEntity) =
            User(a.lastName, a.firstName, a.year, a.groupId, a.subjects, a.university, a.faculty, a.department)
}
