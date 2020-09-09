package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.subject.SubjectEntityToModelConverter
import ro.cadeca.weasylearn.converters.subject.SubjectModelToEntityConverter
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository

@Service
class SubjectService(private val subjectRepository: SubjectRepository,
                     private val subjectEntityToModelConverter: SubjectEntityToModelConverter,
                     private val subjectModelToEntityConverter: SubjectModelToEntityConverter) {

    fun createSubject(subject: Subject) =
            subject.let(subjectModelToEntityConverter::convert)
                    .let(subjectRepository::save)

    fun findAll(): List<Subject> =
            subjectRepository.findAll().map(subjectEntityToModelConverter::convert)
}
