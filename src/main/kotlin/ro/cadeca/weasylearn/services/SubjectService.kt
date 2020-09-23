package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.subject.SubjectFromEntityConverter
import ro.cadeca.weasylearn.converters.subject.SubjectToEntityConverter
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER

@Service
class SubjectService(private val subjectRepository: SubjectRepository,
                     private val userService: UserService,
                     private val subjectFromEntityConverter: SubjectFromEntityConverter,
                     private val subjectToEntityConverter: SubjectToEntityConverter) {

    fun search(query: String?): List<Subject> {
        val findAll = query
                ?.let { subjectRepository.findAllByNameContainingOrCodeContaining(it, it) }
                ?: subjectRepository.findAll()
        return findAll.map(subjectFromEntityConverter::convert)
    }

    fun create(subject: Subject) =
            subject.let(subjectToEntityConverter::convert)
                    .let(subjectRepository::save)

    fun setTeacher(id: Long, username: String) {
        if (!userService.isType(username, TEACHER))
            throw IllegalArgumentException("User $username is not a Teacher")

        subjectRepository.findById(id)
                .map {
                    it.teacher = username
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    IllegalArgumentException("Subject with id $id was not found")
                }
    }

    fun addTutor(id: Long, username: String) {
        if (!userService.exists(username))
            throw IllegalArgumentException("User $username was not found")

        subjectRepository.findById(id)
                .map {
                    it.tutors = it.tutors?.let { it + username } ?: listOf(username)
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    IllegalArgumentException("Subject with id $id was not found")
                }
    }

    fun addStudent(id: Long, username: String) {
        if (!userService.isType(username, STUDENT))
            throw IllegalArgumentException("User $username is not a Student")

        subjectRepository.findById(id)
                .map {
                    it.students = it.students?.let { it + username } ?: listOf(username)
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    IllegalArgumentException("Subject with id $id was not found")
                }
    }
}
