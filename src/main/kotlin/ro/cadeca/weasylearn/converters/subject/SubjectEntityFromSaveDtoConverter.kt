package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity

@Converter
class SubjectEntityFromSaveDtoConverter(
        private val mapperService: MapperService
) : IConverter<SubjectSaveDTO, SubjectEntity> {
    override fun convert(source: SubjectSaveDTO): SubjectEntity = mapperService.map(source)
}
