package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.TeacherDTO
import ro.cadeca.weasylearn.model.Teacher

@Converter
class TeacherToDtoConverter(private val mapperService: MapperService) : IConverter<Teacher, TeacherDTO> {
    override fun convert(source: Teacher): TeacherDTO =
            mapperService.map(source, TeacherDTO::class)
}
