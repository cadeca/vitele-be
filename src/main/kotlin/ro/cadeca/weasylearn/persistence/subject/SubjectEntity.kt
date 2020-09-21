package ro.cadeca.weasylearn.persistence.subject

import ro.cadeca.weasylearn.persistence.BaseEntity
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class SubjectEntity(
        @Column
        var name: String,
        @Column
        var code: String,
        @Column
        var description: String?,
        @Column
        var semester: Int?,
        @Column
        var teacher: String?,
        @ElementCollection
        var tutors: List<String>?,
        @ElementCollection
        var students: List<String>?,
        @OneToMany
        @JoinColumn
        var schedule: List<SubjectScheduleEntity>?
) : BaseEntity()
