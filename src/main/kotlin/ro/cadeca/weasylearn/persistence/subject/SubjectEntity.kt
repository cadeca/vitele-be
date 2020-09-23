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
        var description: String? = null,
        @Column
        var semester: Int? = null,
        @Column
        var teacher: String? = null,
        @ElementCollection
        var tutors: List<String>? = null,
        @ElementCollection
        var students: List<String>? = null,
        @OneToMany
        @JoinColumn
        var schedule: List<SubjectScheduleEntity>? = null
) : BaseEntity()
