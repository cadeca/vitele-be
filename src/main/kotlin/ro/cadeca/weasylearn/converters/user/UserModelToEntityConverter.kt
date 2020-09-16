package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserEntity

@Converter
class UserModelToEntityConverter : IConverter<User, UserEntity> {
    override fun convert(a: User) =
            UserEntity(a.lastName, a.firstName, a.year, a.groupId, a.subjects, a.university, a.faculty, a.department)
}
