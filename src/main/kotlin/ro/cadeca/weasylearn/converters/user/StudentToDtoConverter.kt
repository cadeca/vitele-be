package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.StudentDTO
import ro.cadeca.weasylearn.model.Student

@Converter
class StudentToDtoConverter(private val mapperService: MapperService)
    : IConverter<Student, StudentDTO> {

    override fun convert(source: Student): StudentDTO =
            mapperService.map(source)

}
