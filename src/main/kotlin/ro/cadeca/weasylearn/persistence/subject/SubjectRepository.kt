package ro.cadeca.weasylearn.persistence.subject

import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<SubjectEntity, Long>
