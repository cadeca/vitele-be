package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity

@Converter
class SubjectModelToEntityConverter : IConverter<Subject, SubjectEntity> {
    override fun convert(source: Subject) =
            SubjectEntity(source.name, source.code, source.description, source.semester)
}
