package ro.cadeca.weasylearn.persistence.user

import ro.cadeca.weasylearn.persistence.BaseEntity
import ro.cadeca.weasylearn.persistence.misc.TimeAndLocationEntity
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class StudentsGroupEntity(
        @Column
        var name: String,
        @Column
        var code: String,
        @OneToMany
        @JoinColumn
        var timeAndLocation: List<TimeAndLocationEntity>,
        @ElementCollection
        var students: List<String>
) : BaseEntity()
