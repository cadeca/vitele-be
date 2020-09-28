package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.subject.SubjectEntityFromSaveDtoConverter
import ro.cadeca.weasylearn.converters.subject.SubjectFromEntityConverter
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.exceptions.subject.SubjectNotFoundException
import ro.cadeca.weasylearn.exceptions.user.UserIsNotOfNeededTypeException
import ro.cadeca.weasylearn.exceptions.user.UserNotFoundException
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER

@Service
class SubjectService(private val subjectRepository: SubjectRepository,
                     private val userService: UserService,
                     private val subjectFromEntityConverter: SubjectFromEntityConverter,
                     private val subjectEntityFromSaveDtoConverter: SubjectEntityFromSaveDtoConverter,
                     private val authenticationService: AuthenticationService) {

    fun search(query: String?): List<Subject> {
        return (query
                ?.let { subjectRepository.findAllByNameContainingOrCodeContaining(it, it) }
                ?: subjectRepository.findAll())
                .map(subjectFromEntityConverter::convert)
    }

    fun save(subject: SubjectSaveDTO) =
            subject.let(subjectEntityFromSaveDtoConverter::convert)
                    .let(subjectRepository::save)

    fun setTeacher(id: Long, username: String) {
        if (!userService.isType(username, TEACHER))
            throw UserIsNotOfNeededTypeException(username, TEACHER)

        subjectRepository.findById(id)
                .map {
                    it.teacher = username
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    SubjectNotFoundException(id)
                }
    }

    fun addTutor(id: Long, username: String) {
        if (!userService.exists(username))
            throw UserNotFoundException(username)

        subjectRepository.findById(id)
                .map {
                    it.tutors = it.tutors?.let { it + username } ?: listOf(username)
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    SubjectNotFoundException(id)
                }
    }

    fun addStudent(id: Long, username: String) {
        if (!userService.isType(username, STUDENT))
            throw UserIsNotOfNeededTypeException(username, STUDENT)

        subjectRepository.findById(id)
                .map {
                    it.students = it.students?.let { it + username } ?: listOf(username)
                    it
                }.map {
                    subjectRepository.save(it)
                }.orElseThrow {
                    SubjectNotFoundException(id)
                }
    }

    fun findById(id: Long): Subject {
        return subjectRepository.findById(id)
                .map(subjectFromEntityConverter::convert)
                .orElseThrow { SubjectNotFoundException(id) }
    }

    fun getUsersSubjects(): List<Subject> {
        val username = authenticationService.getKeycloakUser().username
        return subjectRepository.findAllByTeacherOrTutorsOrStudents(username, username, username)
                .map(subjectFromEntityConverter::convert)
    }
}
