package ro.cadeca.weasylearn.persistence.subject

import org.springframework.data.repository.CrudRepository

interface SubjectRepository : CrudRepository<SubjectEntity, Long>
