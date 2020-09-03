package ro.cadeca.vitele.converters.subject

import ro.cadeca.vitele.annotations.Converter
import ro.cadeca.vitele.converters.IConverter
import ro.cadeca.vitele.model.Subject
import ro.cadeca.vitele.persistence.subject.SubjectEntity

@Converter
class SubjectEntityToModelConverter : IConverter<SubjectEntity, Subject> {
    override fun convert(a: SubjectEntity) =
            Subject(a.name, a.code, a.description, a.semester)
}
