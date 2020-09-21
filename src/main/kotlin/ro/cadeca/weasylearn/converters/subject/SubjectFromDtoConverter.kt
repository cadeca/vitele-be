package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.model.Subject

@Converter
class SubjectFromDtoConverter(
        private val mapperService: MapperService
) : IConverter<SubjectDTO, Subject> {
    override fun convert(source: SubjectDTO): Subject =
            mapperService.map(source)
}
