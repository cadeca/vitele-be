package ro.cadeca.weasylearn.persistence.subject

import org.springframework.data.repository.CrudRepository

interface SubjectRepository : CrudRepository<SubjectEntity, Long> {
    fun findAllByNameContainingOrCodeContaining(name: String, code: String): List<SubjectEntity>
    fun findAllByTeacherOrTutorsOrStudents(username1: String, username2: String, username3: String): List<SubjectEntity>
}
