package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.UserDTO
import ro.cadeca.weasylearn.model.User

@Converter
class UserToDtoConverter(private val mapperService: MapperService) : IConverter<User, UserDTO> {
    override fun convert(source: User): UserDTO =
            mapperService.map(source, UserDTO::class)
}
