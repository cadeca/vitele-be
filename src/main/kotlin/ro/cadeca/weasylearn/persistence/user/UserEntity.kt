package ro.cadeca.weasylearn.persistence.user

import ro.cadeca.weasylearn.persistence.BaseEntity
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class UserEntity(
        @Column
        var lastName: String? = null,

        @Column
        var firstName: String? = null,

        @Column
        var year: Int? = null,

        @Column
        var groupId: String? = null,

        @OneToMany
        @JoinColumn(name = "subjects")
        var subjects: List<SubjectEntity>? = null,

        @Column
        var university: String? = null,

        @Column
        var faculty: String? = null,

        @Column
        var department: String? = null
) : BaseEntity()
