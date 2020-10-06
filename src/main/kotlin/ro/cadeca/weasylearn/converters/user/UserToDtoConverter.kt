package ro.cadeca.weasylearn.converters.user

import ma.glasnost.orika.impl.DefaultMapperFactory
import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.StudentDTO
import ro.cadeca.weasylearn.dto.TeacherDTO
import ro.cadeca.weasylearn.dto.UserDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.model.User

@Converter
class UserToDtoConverter(private val mapperService: MapperService) : IConverter<User, UserDTO> {

    init {
        val mapperFacade = DefaultMapperFactory.Builder().build().mapperFacade
        mapperService.registerCustomFactory { source, _ ->
            when (source) {
                is Teacher -> source.let { mapperFacade.map(source, TeacherDTO::class.java) }
                is Student -> source.let { mapperFacade.map(source, StudentDTO::class.java) }
                else -> source.let { mapperFacade.map(source, UserDTO::class.java) }
            }
        }
    }

    override fun convert(source: User): UserDTO =
            mapperService.map(source)
}
