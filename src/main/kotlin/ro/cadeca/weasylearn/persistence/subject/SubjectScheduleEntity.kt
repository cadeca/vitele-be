package ro.cadeca.weasylearn.persistence.subject

import ro.cadeca.weasylearn.persistence.BaseEntity
import ro.cadeca.weasylearn.persistence.misc.TimeAndLocationEntity
import ro.cadeca.weasylearn.persistence.user.StudentsGroupEntity
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class SubjectScheduleEntity(
        @OneToMany
        @JoinColumn
        var timeAndLocation: List<TimeAndLocationEntity>,
        @Column
        var tutor: String,
        @OneToOne
        var studentsGroup: StudentsGroupEntity
) : BaseEntity()
