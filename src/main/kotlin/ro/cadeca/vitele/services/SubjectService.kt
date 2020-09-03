package ro.cadeca.vitele.services

import org.springframework.stereotype.Service
import ro.cadeca.vitele.converters.subject.SubjectEntityToModelConverter
import ro.cadeca.vitele.converters.subject.SubjectModelToEntityConverter
import ro.cadeca.vitele.model.Subject
import ro.cadeca.vitele.persistence.subject.SubjectRepository

@Service
class SubjectService(private val subjectRepository: SubjectRepository,
                     private val subjectEntityToModelConverter: SubjectEntityToModelConverter,
                     private val subjectModelToEntityConverter: SubjectModelToEntityConverter) {

    fun createSubject(subject: Subject) {
        subject.let(subjectModelToEntityConverter::convert)
                .let(subjectRepository::save)
    }

    fun findAll(): List<Subject> = subjectRepository.findAll().map(subjectEntityToModelConverter::convert)
}