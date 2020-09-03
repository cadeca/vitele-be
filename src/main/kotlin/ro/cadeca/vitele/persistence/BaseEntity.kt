package ro.cadeca.vitele.persistence

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

//    @CreatedDate
//    @Column(name = "created_date", nullable = false, updatable = false)
//    lateinit var createdDate: LocalDateTime

//    @LastModifiedDate
//    @Column(name = "last_modified_date", nullable = false)
//    lateinit var lastModifiedDate: LocalDateTime

    @Column
    var active = true

    @Column
    var deleted = false
        private set

    fun delete() {
        deleted = true
    }
}
