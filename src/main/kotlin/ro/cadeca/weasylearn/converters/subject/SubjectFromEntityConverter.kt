package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity

@Converter
class SubjectFromEntityConverter(
        private val mapperService: MapperService
) : IConverter<SubjectEntity, Subject> {
    override fun convert(source: SubjectEntity): Subject =
            mapperService.map(source)
}
