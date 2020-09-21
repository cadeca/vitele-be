package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.subject.SubjectFromEntityConverter
import ro.cadeca.weasylearn.converters.subject.SubjectToEntityConverter
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository

@Service
class SubjectService(private val subjectRepository: SubjectRepository,
                     private val subjectFromEntityConverter: SubjectFromEntityConverter,
                     private val subjectToEntityConverter: SubjectToEntityConverter) {

    fun createSubject(subject: Subject) =
            subject.let(subjectToEntityConverter::convert)
                    .let(subjectRepository::save)

    fun findAll(query: String?): List<Subject> {
        val findAll = query
                ?.let { subjectRepository.findAllByNameContainingOrCodeContaining(it, it) }
                ?: subjectRepository.findAll()
        return findAll.map(subjectFromEntityConverter::convert)
    }
}
