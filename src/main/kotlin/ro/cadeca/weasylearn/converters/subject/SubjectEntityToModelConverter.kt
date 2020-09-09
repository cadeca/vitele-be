package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity

@Converter
class SubjectEntityToModelConverter : IConverter<SubjectEntity, Subject> {
    override fun convert(a: SubjectEntity) =
            Subject(a.name, a.code, a.description, a.semester)
}
