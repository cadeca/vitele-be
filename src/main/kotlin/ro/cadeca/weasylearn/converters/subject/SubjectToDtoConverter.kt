package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.model.Subject

@Converter
class SubjectToDtoConverter(
        private val mapperService: MapperService
) : IConverter<Subject, SubjectDTO> {
    override fun convert(source: Subject): SubjectDTO = mapperService.map(source)
}
