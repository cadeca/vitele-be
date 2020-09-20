package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.dto.UserWrapperDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER

@Converter
class UserToWrapperDtoConverter(private val studentToDtoConverter: StudentToDtoConverter,
                                private val teacherToDtoConverter: TeacherToDtoConverter,
                                private val userToDtoConverter: UserToDtoConverter) : IConverter<User, UserWrapperDTO> {
    override fun convert(source: User): UserWrapperDTO {
        return when (source) {
            is Student -> UserWrapperDTO(STUDENT, studentToDtoConverter.convert(source))
            is Teacher -> UserWrapperDTO(TEACHER, teacherToDtoConverter.convert(source))
            else -> UserWrapperDTO(USER, userToDtoConverter.convert(source))
        }

    }

}
