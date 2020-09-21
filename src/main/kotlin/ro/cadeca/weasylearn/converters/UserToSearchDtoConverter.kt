package ro.cadeca.weasylearn.converters

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.dto.UserSearchDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.StudyType

@Converter
class UserToSearchDtoConverter(private val mapperService: MapperService) : IConverter<User, UserSearchDTO> {
    override fun convert(source: User): UserSearchDTO {
        return mapperService.map(source, UserSearchDTO::class).also { it.title = extractTitle(source) }
    }

    private fun extractTitle(source: User): String {
        return when (source) {
            is Student -> StudyType.getStudentTitle(source)
            is Teacher -> source.titles?.first() ?: ""
            else -> ""
        }
    }

}
