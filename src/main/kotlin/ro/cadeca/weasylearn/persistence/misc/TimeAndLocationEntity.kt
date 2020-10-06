package ro.cadeca.weasylearn.persistence.misc

import ro.cadeca.weasylearn.persistence.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class TimeAndLocationEntity(
        @Column
        var location: String,
        @Column
        var dateTime: ZonedDateTime
) : BaseEntity()
