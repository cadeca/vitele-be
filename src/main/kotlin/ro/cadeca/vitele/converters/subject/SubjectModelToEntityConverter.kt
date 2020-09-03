package ro.cadeca.vitele.converters.subject

import ro.cadeca.vitele.annotations.Converter
import ro.cadeca.vitele.converters.IConverter
import ro.cadeca.vitele.model.Subject
import ro.cadeca.vitele.persistence.subject.SubjectEntity

@Converter
class SubjectModelToEntityConverter : IConverter<Subject, SubjectEntity> {
    override fun convert(a: Subject) =
            SubjectEntity(a.name, a.code, a.description, a.semester)
}
