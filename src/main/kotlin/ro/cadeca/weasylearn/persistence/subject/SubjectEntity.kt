package ro.cadeca.weasylearn.persistence.subject

import ro.cadeca.weasylearn.persistence.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class SubjectEntity(
        @Column
        var name: String? = null,

        @Column
        var code: String? = null,

        @Column
        var description: String? = null,

        @Column
        var semester: Int? = null
) : BaseEntity()
